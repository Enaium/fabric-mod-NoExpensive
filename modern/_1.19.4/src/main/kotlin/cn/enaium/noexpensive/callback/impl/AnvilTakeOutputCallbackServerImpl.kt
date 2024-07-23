package cn.enaium.noexpensive.callback.impl

import net.minecraft.entity.player.PlayerEntity

/**
 * @author Enaium
 */
class AnvilTakeOutputCallbackServerImpl : AnvilTakeOutputCallbackImpl() {
    override fun condition(player: PlayerEntity): Boolean {
        return player.abilities.creativeMode
    }
}