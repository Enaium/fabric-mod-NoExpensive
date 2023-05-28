package cn.enaium.noexpensive.mixin;

import net.minecraft.container.*;
import net.minecraft.enchantment.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author Enaium
 */
@Mixin(AnvilContainer.class)
public abstract class AnvilContainerMixin extends Container {

    protected AnvilContainerMixin(@Nullable ContainerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/container/Property;get()I"), method = "updateResult")
    private int get(Property property) {
        if (property.get() >= 40) {
            property.set(39);
            return 39;
        }
        return property.get();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isDifferent(Lnet/minecraft/enchantment/Enchantment;)Z"), method = "updateResult")
    private boolean isDifferent(Enchantment enchantment, Enchantment other) {
        return isDifferent0(enchantment, other);
    }

    private boolean isDifferent0(Enchantment enchantment1, Enchantment enchantment2) {
        if ((enchantment1 instanceof InfinityEnchantment && enchantment2 instanceof MendingEnchantment) || (enchantment2 instanceof InfinityEnchantment && enchantment1 instanceof MendingEnchantment)) {
            return true;
        } else if ((enchantment1 instanceof MultishotEnchantment && enchantment2 instanceof PiercingEnchantment) || (enchantment2 instanceof MultishotEnchantment && enchantment1 instanceof PiercingEnchantment)) {
            return true;
        }
        return enchantment1.isDifferent(enchantment2);
    }
}
