package cn.enaium.noexpensive.mixin;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author Enaium
 */
@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/AnvilScreenHandler;getLevelCost()I"), method = "drawForeground")
    private int drawForeground(AnvilScreenHandler anvilScreenHandler) {
        if (anvilScreenHandler.getLevelCost() >= 40) {
            return 39;
        }
        return anvilScreenHandler.getLevelCost();
    }
}
