package cn.enaium.noexpensive.callback

import net.legacyfabric.fabric.api.event.EventFactory
import net.minecraft.item.ItemStack
import java.util.function.Function

/**
 * @author Enaium
 */
fun interface AnvilTakeOutputCallback {
    fun interact(output: ItemStack)

    companion object {
        val EVENT = EventFactory.createArrayBacked(
            AnvilTakeOutputCallback::class.java
        ) { listeners: Array<AnvilTakeOutputCallback> ->
            AnvilTakeOutputCallback { output: ItemStack ->
                for (listener in listeners) {
                    listener.interact(output)
                }
            }
        }
    }
}
