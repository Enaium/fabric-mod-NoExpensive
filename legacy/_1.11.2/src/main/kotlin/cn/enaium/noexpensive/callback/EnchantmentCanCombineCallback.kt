package cn.enaium.noexpensive.callback

import net.legacyfabric.fabric.api.event.EventFactory
import net.minecraft.enchantment.Enchantment
import net.minecraft.util.ActionResult
import java.util.function.Function

/**
 * @author Enaium
 */
fun interface EnchantmentCanCombineCallback {
    fun interact(enchantment1: Enchantment, enchantment2: Enchantment): ActionResult

    companion object {
        val EVENT = EventFactory.createArrayBacked(
            EnchantmentCanCombineCallback::class.java
        ) { listeners: Array<EnchantmentCanCombineCallback> ->
            EnchantmentCanCombineCallback { enchantment1: Enchantment, enchantment2: Enchantment ->
                for (listener in listeners) {
                    val result: ActionResult = listener.interact(enchantment1, enchantment2)
                    if (result != ActionResult.PASS) {
                        return@EnchantmentCanCombineCallback result
                    }
                }
                ActionResult.PASS
            }
        }
    }
}
