package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.NoExpensive;
import net.minecraft.container.AnvilContainer;
import net.minecraft.container.Property;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author Enaium
 */
@Mixin(AnvilContainer.class)
public abstract class AnvilContainerMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/container/Property;get()I"), method = "updateResult")
    private int get(Property property) {
        if (Config.getModel().maxLevel > 0) {
            return Math.min(property.get(), Config.getModel().maxLevel);
        }
        return property.get();
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z", ordinal = 1), method = "updateResult")
    private boolean creativeMode(PlayerAbilities instance) {
        return true;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isDifferent(Lnet/minecraft/enchantment/Enchantment;)Z"), method = "updateResult")
    private boolean isDifferent(Enchantment enchantment, Enchantment other) {
        return NoExpensive.isDifferent0(enchantment, other);
    }
}
