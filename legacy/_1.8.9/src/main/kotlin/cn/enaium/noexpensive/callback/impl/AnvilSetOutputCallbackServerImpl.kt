package cn.enaium.noexpensive.callback.impl

import net.minecraft.entity.player.PlayerEntity

/**
 * @author Enaium
 */
class AnvilSetOutputCallbackServerImpl : AnvilSetOutputCallbackImpl() {
    override fun condition(player: PlayerEntity): Boolean {
        return player.abilities.creativeMode
    }
}