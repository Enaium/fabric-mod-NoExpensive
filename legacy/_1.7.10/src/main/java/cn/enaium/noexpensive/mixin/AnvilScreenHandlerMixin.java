package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.callback.AnvilSetOutputCallback;
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Enaium
 */
@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ScreenHandler {
    @Shadow
    private Inventory resultInventory;

    @Shadow
    public int repairCost;

    @Shadow
    @Final
    private PlayerEntity player;

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/screen/AnvilScreenHandler;repairCost:I", opcode = Opcodes.GETFIELD), method = "updateResult")
    private int repairCost(AnvilScreenHandler instance) {
        if (Config.getModel().maxLevel > 0) {
            return Math.min(Math.abs(instance.repairCost), Config.getModel().maxLevel);
        }
        return Math.abs(instance.repairCost);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;setInvStack(ILnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER, ordinal = 3), method = "updateResult")
    public void setStack(CallbackInfo ci) {
        ItemStack o = resultInventory.getInvStack(0);
        AnvilSetOutputCallback.EVENT.invoker().interact(o, Config.getModel().maxLevel > 0 ? Math.min(Math.abs(repairCost), Config.getModel().maxLevel) : (repairCost), getSlot(2).canTakeItems(player));
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z", ordinal = 1), method = "updateResult")
    private boolean creativeMode(PlayerAbilities instance) {
        return true;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;differs(Lnet/minecraft/enchantment/Enchantment;)Z"), method = "updateResult")
    private boolean isDifferent(Enchantment enchantment, Enchantment other) {
        return EnchantmentCanCombineCallback.EVENT.invoker().interact(enchantment, other);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaximumLevel()I"), method = "updateResult")
    public int getMaxLevel(Enchantment instance) {
        //though it's the type of the max level is short, but it will be cast to byte when it's used
        return 255;
    }
}
