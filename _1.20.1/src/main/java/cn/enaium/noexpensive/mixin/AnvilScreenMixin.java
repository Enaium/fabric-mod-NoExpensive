package cn.enaium.noexpensive.mixin;

import cn.enaium.noexpensive.Config;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.entity.player.PlayerAbilities;
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
        if (Config.getModel().maxLevel == 0) {
            anvilScreenHandler.getLevelCost();
        }
        return Config.getModel().maxLevel;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z"), method = "drawForeground")
    private boolean creativeMode(PlayerAbilities instance) {
        return true;
    }
}
