package cn.enaium.noexpensive.mixin;

import callback.AnvilTakeOutputCallback;
import net.minecraft.container.AnvilContainer;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
@Mixin(Container.class)
public class ContainerMixin {
    @Shadow
    @Final
    public List<Slot> slots;

    @SuppressWarnings({"ConstantValue", "EqualsBetweenInconvertibleTypes"})
    @Inject(at = @At("HEAD"), method = "onSlotClick")
    public void transferSlot(int slotId, int clickData, SlotActionType actionType, PlayerEntity playerEntity, CallbackInfoReturnable<ItemStack> cir) {
        if (slotId == 2 && this.getClass().equals(AnvilContainer.class) && slots.get(2).canTakeItems(playerEntity)) {
            AnvilTakeOutputCallback.Companion.getEVENT().invoker().interact(slots.get(2).getStack());
        }
    }
}
