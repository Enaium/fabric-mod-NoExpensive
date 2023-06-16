package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.Config;
import net.minecraft.container.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author Enaium
 */
@Mixin(targets = "net.minecraft.container.AnvilContainer$2")
public class AnvilContainerSlotMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/container/Property;get()I"), method = "canTakeItems")
    private int canTakeOutput(Property property) {
        if (Config.getModel().maxLevel > 0) {
            return Math.min(property.get(), Config.getModel().maxLevel);
        }
        return property.get();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/container/Property;get()I"), method = "onTakeItem")
    private int onTakeOutput(Property property) {
        if (Config.getModel().maxLevel > 0) {
            return Math.min(property.get(), Config.getModel().maxLevel);
        }
        return property.get();
    }
}
