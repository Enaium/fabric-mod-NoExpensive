package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

/**
 * @author Enaium
 */
class AnvilTakeOutputCallbackImpl : AnvilTakeOutputCallback {
    override fun interact(output: ItemStack, player: PlayerEntity) {
        MinecraftClient.getInstance().player == player || player.abilities.creativeMode && return
        val nbtCompound = output.getOrCreateSubTag("display")
        val list = nbtCompound.getList("Lore", 8)
        for (i in list.indices) {
            if (list.getString(i).contains("container.repair.cost")) {
                list.removeAt(i)
                break
            }
        }
    }
}
