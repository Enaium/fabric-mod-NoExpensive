package cn.enaium.noexpensive.mixin;

import net.minecraft.enchantment.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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
        if (property.get() >= 40) {
            property.set(39);
            return 39;
        }
        return property.get();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;canCombine(Lnet/minecraft/enchantment/Enchantment;)Z"), method = "updateResult")
    private boolean canCombine(Enchantment enchantment, Enchantment other) {
        return canCombine0(enchantment, other);
    }

    private boolean canCombine0(Enchantment enchantment1, Enchantment enchantment2) {
        if ((enchantment1 instanceof InfinityEnchantment && enchantment2 instanceof MendingEnchantment) || (enchantment2 instanceof InfinityEnchantment && enchantment1 instanceof MendingEnchantment)) {
            return true;
        } else if ((enchantment1 instanceof MultishotEnchantment && enchantment2 instanceof PiercingEnchantment) || (enchantment2 instanceof MultishotEnchantment && enchantment1 instanceof PiercingEnchantment)) {
            return true;
        }
        return enchantment1.canCombine(enchantment2);
    }
}
