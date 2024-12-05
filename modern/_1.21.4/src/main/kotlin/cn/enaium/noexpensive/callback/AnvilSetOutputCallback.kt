package cn.enaium.noexpensive.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

/**
 * @author Enaium
 */
fun interface AnvilSetOutputCallback {
    fun interact(output: ItemStack, levelCost: Int, canTake: Boolean, player: PlayerEntity)

    companion object {
        val EVENT: Event<AnvilSetOutputCallback> =
            EventFactory.createArrayBacked(AnvilSetOutputCallback::class.java) { listeners: Array<AnvilSetOutputCallback> ->
                AnvilSetOutputCallback { output: ItemStack, levelCost: Int, canTake: Boolean, player: PlayerEntity ->
                    for (listener in listeners) {
                        listener.interact(output, levelCost, canTake, player)
                    }
                }
            }
    }
}
