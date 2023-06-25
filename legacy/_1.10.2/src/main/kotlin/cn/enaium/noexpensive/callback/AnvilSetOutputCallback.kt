package cn.enaium.noexpensive.callback

import net.legacyfabric.fabric.api.event.EventFactory
import net.minecraft.item.ItemStack

/**
 * @author Enaium
 */
fun interface AnvilSetOutputCallback {
    fun interact(output: ItemStack?, levelCost: Int, canTake: Boolean)

    companion object {
        val EVENT = EventFactory.createArrayBacked(
            AnvilSetOutputCallback::class.java
        ) { listeners: Array<AnvilSetOutputCallback> ->
            AnvilSetOutputCallback { output: ItemStack?, levelCost: Int, canTake: Boolean ->
                for (listener in listeners) {
                    listener.interact(output, levelCost, canTake)
                }
            }
        }
    }
}
