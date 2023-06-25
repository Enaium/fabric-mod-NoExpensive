package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.Config;
import net.minecraft.screen.AnvilScreenHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author Enaium
 */
@Mixin(targets = "net.minecraft.screen.AnvilScreenHandler$2")
public class AnvilScreenHandlerSlotMixin {
    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/screen/AnvilScreenHandler;repairCost:I", opcode = Opcodes.GETFIELD), method = "canTakeItems")
    private int canTakeItems(AnvilScreenHandler instance) {
        if (Config.INSTANCE.getModel().getMaxLevel() > 0) {
            return Math.min(Math.abs(instance.repairCost), Config.INSTANCE.getModel().getMaxLevel());
        }
        return Math.abs(instance.repairCost);
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/screen/AnvilScreenHandler;repairCost:I", opcode = Opcodes.GETFIELD), method = "onTakeItem")
    private int onTakeItems(AnvilScreenHandler instance) {
        if (Config.INSTANCE.getModel().getMaxLevel() > 0) {
            return Math.min(Math.abs(instance.repairCost), Config.INSTANCE.getModel().getMaxLevel());
        }
        return Math.abs(instance.repairCost);
    }
}
