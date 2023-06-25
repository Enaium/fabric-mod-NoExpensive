package cn.enaium.noexpensive.callback

import net.legacyfabric.fabric.api.event.EventFactory
import net.minecraft.enchantment.Enchantment

/**
 * @author Enaium
 */
fun interface EnchantmentCanCombineCallback {
    fun interact(enchantment1: Enchantment, enchantment2: Enchantment): Boolean

    companion object {
        val EVENT = EventFactory.createArrayBacked(
            EnchantmentCanCombineCallback::class.java
        ) { listeners: Array<EnchantmentCanCombineCallback> ->
            EnchantmentCanCombineCallback { enchantment1: Enchantment, enchantment2: Enchantment ->
                for (listener in listeners) {
                    val result = listener.interact(enchantment1, enchantment2)
                    if (!result) {
                        return@EnchantmentCanCombineCallback false
                    }
                }
                return@EnchantmentCanCombineCallback true
            }
        }
    }
}
