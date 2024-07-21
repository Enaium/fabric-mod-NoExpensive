package cn.enaium.noexpensive.callback

import net.legacyfabric.fabric.api.event.EventFactory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

/**
 * @author Enaium
 */
fun interface AnvilTakeOutputCallback {
    fun interact(output: ItemStack, player: PlayerEntity)

    companion object {
        val EVENT = EventFactory.createArrayBacked(
            AnvilTakeOutputCallback::class.java
        ) { listeners: Array<AnvilTakeOutputCallback> ->
            AnvilTakeOutputCallback { output: ItemStack, player: PlayerEntity ->
                for (listener in listeners) {
                    listener.interact(output, player)
                }
            }
        }
    }
}
