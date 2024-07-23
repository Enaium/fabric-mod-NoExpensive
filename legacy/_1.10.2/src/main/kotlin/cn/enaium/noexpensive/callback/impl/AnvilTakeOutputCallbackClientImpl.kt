package cn.enaium.noexpensive.callback.impl

import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity

/**
 * @author Enaium
 */
class AnvilTakeOutputCallbackClientImpl : AnvilTakeOutputCallbackImpl() {
    override fun condition(player: PlayerEntity): Boolean {
        return MinecraftClient.getInstance().player == player || player.abilities.creativeMode
    }
}