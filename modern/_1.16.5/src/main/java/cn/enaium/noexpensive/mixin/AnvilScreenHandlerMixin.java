package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.callback.AnvilSetOutputCallback;
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**
 * @author Enaium
 */
@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Shadow
    @Final
    private Property levelCost;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

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
        AnvilSetOutputCallback.Companion.getEVENT().invoker().interact(o, levelCost.get(), canTakeOutput(player, false), player);
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z", ordinal = 1), method = "updateResult")
    private boolean creativeMode(PlayerAbilities instance) {
        return true;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;canCombine(Lnet/minecraft/enchantment/Enchantment;)Z"), method = "updateResult")
    private boolean canCombine(Enchantment enchantment, Enchantment other) {
        return EnchantmentCanCombineCallback.Companion.getEVENT().invoker().interact(enchantment, other) == ActionResult.PASS;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"), method = "updateResult")
    public int getMaxLevel(Enchantment instance) {
        //though it's the type of the max level is short, but it will be cast to byte when it's used
        return 255;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), method = "updateResult")
    public Object intValue(Map<Enchantment, Integer> instance, Object key, Object defaultValue) {
        final Enchantment enchantment = (Enchantment) key;
        final Integer integer = ((Integer) defaultValue);

        final Integer orDefault = instance.getOrDefault(enchantment, integer);
        if (orDefault + 1 > enchantment.getMaxLevel() && !Config.INSTANCE.getModel().getCombineHigher()) {
            return 0;
        }
        return orDefault;
    }
}
