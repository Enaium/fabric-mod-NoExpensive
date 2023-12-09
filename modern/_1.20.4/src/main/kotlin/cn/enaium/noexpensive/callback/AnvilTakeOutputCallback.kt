package cn.enaium.noexpensive.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.item.ItemStack

/**
 * @author Enaium
 */
fun interface AnvilTakeOutputCallback {
    fun interact(output: ItemStack)

    companion object {
        val EVENT: Event<AnvilTakeOutputCallback> =
            EventFactory.createArrayBacked(AnvilTakeOutputCallback::class.java) { listeners: Array<AnvilTakeOutputCallback> ->
                AnvilTakeOutputCallback { output: ItemStack ->
                    for (listener in listeners) {
                        listener.interact(output)
                    }
                }
            }
    }
}
