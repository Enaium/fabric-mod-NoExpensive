package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * @author Enaium
 */
@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {
    @Shadow
    @Final
    public List<Slot> slots;

    @SuppressWarnings({"ConstantValue", "EqualsBetweenInconvertibleTypes"})
    @Inject(at = @At("HEAD"), method = "onSlotClick")
    public void onSlotClick(int slotIndex, int j, SlotActionType actionType, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        if (slotIndex == 2 && this.getClass().equals(AnvilScreenHandler.class) && slots.get(2).canTakeItems(player)) {
            AnvilTakeOutputCallback.Companion.getEVENT().invoker().interact(slots.get(2).getStack());
        }
    }
}
