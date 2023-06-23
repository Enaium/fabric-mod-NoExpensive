package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.slot.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ItemAction;
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
public class ScreenHandlerMixin {
    @Shadow
    public List<Slot> slots;

    @SuppressWarnings({"ConstantValue", "EqualsBetweenInconvertibleTypes"})
    @Inject(at = @At("HEAD"), method = "method_3252")
    public void transferSlot(int slotId, int j, ItemAction itemAction, PlayerEntity playerEntity, CallbackInfoReturnable<ItemStack> cir) {
        if (slotId == 2 && this.getClass().equals(AnvilScreenHandler.class) && slots.get(2).canTakeItems(playerEntity)) {
            AnvilTakeOutputCallback.EVENT.invoker().interact(slots.get(2).getStack());
        }
    }
}
