package callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.item.ItemStack
import java.util.function.Function

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
