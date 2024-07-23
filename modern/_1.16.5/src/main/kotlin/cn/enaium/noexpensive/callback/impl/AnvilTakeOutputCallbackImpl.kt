package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

/**
 * @author Enaium
 */
abstract class AnvilTakeOutputCallbackImpl : AnvilTakeOutputCallback {
    override fun interact(output: ItemStack, player: PlayerEntity) {
        condition(player) && return
        val nbtCompound = output.getOrCreateSubTag("display")
        val list = nbtCompound.getList("Lore", 8)
        for (i in list.indices) {
            if (list.getString(i).contains("container.repair.cost")) {
                list.removeAt(i)
                break
            }
        }
    }

    abstract fun condition(player: PlayerEntity): Boolean
}