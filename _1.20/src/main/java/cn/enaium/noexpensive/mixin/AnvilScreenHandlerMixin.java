package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.NoExpensive;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.screen.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.Objects;

/**
 * @author Enaium
 */
@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    public AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;get()I"), method = "updateResult")
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

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;canCombine(Lnet/minecraft/enchantment/Enchantment;)Z"), method = "updateResult")
    private boolean canCombine(Enchantment enchantment, Enchantment other) {
        return NoExpensive.canCombine0(enchantment, other);
    }
}
