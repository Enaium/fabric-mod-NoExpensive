/*
 * Copyright (c) 2026 Enaium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.event.ScreenCallbacks;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.enaium.noexpensive.utility.PlayerKt.toCommon;

/**
 * @author Enaium
 */
@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

    public AnvilMenuMixin(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition itemInputSlots) {
        super(menuType, containerId, inventory, access, itemInputSlots);
    }

    @Shadow
    public abstract int getCost();

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;get()I"), method = "mayPickup")
    private int mayPickup(DataSlot instance) {
        if (Config.INSTANCE.getModel().getMaxLevel() > 0) {
            return Math.min(Math.abs(instance.get()), Config.INSTANCE.getModel().getMaxLevel());
        }
        return Math.abs(instance.get());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;get()I"), method = "onTake")
    private int onTake(DataSlot instance) {
        if (Config.INSTANCE.getModel().getMaxLevel() > 0) {
            return Math.min(Math.abs(instance.get()), Config.INSTANCE.getModel().getMaxLevel());
        }
        return Math.abs(instance.get());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;get()I"), method = "createResult")
    private int get(DataSlot instance) {
        if (Config.INSTANCE.getModel().getMaxLevel() > 0) {
            return Math.min(Math.abs(instance.get()), Config.INSTANCE.getModel().getMaxLevel());
        }
        return Math.abs(instance.get());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;set(I)V", shift = At.Shift.AFTER, ordinal = 4), method = "createResult")
    public void set(CallbackInfo ci) {
        ItemStack o = resultSlots.getItem(0);
        if (!o.equals(ItemStack.EMPTY)) {
            ScreenCallbacks.AnvilSetOutputCallback.Companion.getEVENT().getInvoker().set(toCommon(player), getCost(), mayPickup(player, false));
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hasInfiniteMaterials()Z", ordinal = 1), method = "createResult")
    private boolean creativeMode(Player instance) {
        return true;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;get()I"), method = "getCost")
    private int getCost(DataSlot instance) {
        if (Config.INSTANCE.getModel().getMaxLevel() > 0) {
            return Math.min(Math.abs(instance.get()), Config.INSTANCE.getModel().getMaxLevel());
        }
        return Math.abs(instance.get());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;areCompatible(Lnet/minecraft/core/Holder;Lnet/minecraft/core/Holder;)Z"), method = "createResult")
    private boolean areCompatible(Holder<Enchantment> enchantment, Holder<Enchantment> other) {
        if (!ScreenCallbacks.EnchantmentCanCombineCallback.Companion.getEVENT().getInvoker().canCombine(enchantment.getRegisteredName(), other.getRegisteredName())) {
            return Enchantment.areCompatible(enchantment, other);
        }
        return true;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"), method = "createResult")
    public int getMaxLevel(Enchantment instance) {
        //though it's the type of the max level is short, but it will be cast to byte when it's used
        return Config.INSTANCE.getModel().getCombineHigher() ? 255 : instance.getMaxLevel();
    }
}
