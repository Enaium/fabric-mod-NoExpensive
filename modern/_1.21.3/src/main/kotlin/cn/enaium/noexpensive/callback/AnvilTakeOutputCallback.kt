package cn.enaium.noexpensive.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

/**
 * @author Enaium
 */
fun interface AnvilTakeOutputCallback {
    fun interact(output: ItemStack, player: PlayerEntity)

    companion object {
        val EVENT: Event<AnvilTakeOutputCallback> =
            EventFactory.createArrayBacked(AnvilTakeOutputCallback::class.java) { listeners: Array<AnvilTakeOutputCallback> ->
                AnvilTakeOutputCallback { output: ItemStack, player: PlayerEntity ->
                    for (listener in listeners) {
                        listener.interact(output, player)
                    }
                }
            }
    }
}
