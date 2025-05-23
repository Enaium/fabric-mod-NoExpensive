package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.callback.AnvilSetOutputCallback;
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;
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
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ForgingSlotsManager forgingSlotsManager) {
        super(type, syncId, playerInventory, context, forgingSlotsManager);
    }

    @Shadow
    public abstract int getLevelCost();

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;get()I"), method = "canTakeOutput")
    private int canTakeOutput(Property property) {
        if (Config.INSTANCE.getModel().getMaxLevel() > 0) {
            return Math.min(Math.abs(property.get()), Config.INSTANCE.getModel().getMaxLevel());
        }
        return Math.abs(property.get());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;get()I"), method = "onTakeOutput")
    private int onTakeOutput(Property property) {
        if (Config.INSTANCE.getModel().getMaxLevel() > 0) {
            return Math.min(Math.abs(property.get()), Config.INSTANCE.getModel().getMaxLevel());
        }
        return Math.abs(property.get());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;get()I"), method = "updateResult")
    private int get(Property property) {
        if (Config.INSTANCE.getModel().getMaxLevel() > 0) {
            return Math.min(Math.abs(property.get()), Config.INSTANCE.getModel().getMaxLevel());
        }
        return Math.abs(property.get());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER, ordinal = 4), method = "updateResult")
    public void setStack(CallbackInfo ci) {
        ItemStack o = output.getStack(0);
        AnvilSetOutputCallback.Companion.getEVENT().invoker().interact(o, getLevelCost(), canTakeOutput(player, false), player);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isInCreativeMode()Z", ordinal = 1), method = "updateResult")
    private boolean creativeMode(PlayerEntity instance) {
        return true;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;get()I"), method = "getLevelCost")
    private int getLevelCost(Property property) {
        if (Config.INSTANCE.getModel().getMaxLevel() > 0) {
            return Math.min(Math.abs(property.get()), Config.INSTANCE.getModel().getMaxLevel());
        }
        return Math.abs(property.get());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;canBeCombined(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/registry/entry/RegistryEntry;)Z"), method = "updateResult")
    private boolean canCombine(RegistryEntry<Enchantment> enchantment, RegistryEntry<Enchantment> other) {
        return EnchantmentCanCombineCallback.Companion.getEVENT().invoker().interact(enchantment, other) == ActionResult.PASS;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"), method = "updateResult")
    public int getMaxLevel(Enchantment instance) {
        //though it's the type of the max level is short, but it will be cast to byte when it's used
        return Config.INSTANCE.getModel().getCombineHigher() ? 255 : instance.getMaxLevel();
    }
}
