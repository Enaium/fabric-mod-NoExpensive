package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.callback.AnvilSetOutputCallback;
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback;
import net.minecraft.container.AnvilContainer;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.container.Property;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;
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
@Mixin(AnvilContainer.class)
public abstract class AnvilContainerMixin extends Container {
    @Shadow
    @Final
    private Inventory result;

    protected AnvilContainerMixin(@Nullable ContainerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Shadow
    public abstract int getLevelCost();

    @Shadow
    @Final
    private PlayerEntity player;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/container/Property;get()I"), method = "updateResult")
    private int get(Property property) {
        if (Config.getModel().maxLevel > 0) {
            return Math.min(property.get(), Config.getModel().maxLevel);
        }
        return property.get();
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;setInvStack(ILnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER, ordinal = 4), method = "updateResult")
    public void setStack(CallbackInfo ci) {
        ItemStack o = result.getInvStack(0);
        AnvilSetOutputCallback.EVENT.invoker().interact(o, getLevelCost(), slots.get(2).canTakeItems(player));
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z", ordinal = 1), method = "updateResult")
    private boolean creativeMode(PlayerAbilities instance) {
        return true;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/container/Property;get()I"), method = "getLevelCost")
    private int getLevelCost(Property property) {
        if (Config.getModel().maxLevel > 0) {
            return Math.min(property.get(), Config.getModel().maxLevel);
        }
        return property.get();
    }


    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isDifferent(Lnet/minecraft/enchantment/Enchantment;)Z"), method = "updateResult")
    private boolean isDifferent(Enchantment enchantment, Enchantment other) {
        return EnchantmentCanCombineCallback.EVENT.invoker().interact(enchantment, other) == ActionResult.PASS;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaximumLevel()I"), method = "updateResult")
    public int getMaxLevel(Enchantment instance) {
        //though it's the type of the max level is short, but it will be cast to byte when it's used
        return 255;
    }
}
