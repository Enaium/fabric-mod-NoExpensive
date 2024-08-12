package cn.enaium.noexpensive.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.util.ActionResult

/**
 * @author Enaium
 */
fun interface EnchantmentCanCombineCallback {
    fun interact(enchantment1: RegistryEntry<Enchantment>, enchantment2: RegistryEntry<Enchantment>): ActionResult

    companion object {
        val EVENT: Event<EnchantmentCanCombineCallback> =
            EventFactory.createArrayBacked(EnchantmentCanCombineCallback::class.java) { listeners ->
                EnchantmentCanCombineCallback { enchantment1, enchantment2 ->
                    for (listener in listeners) {
                        val result = listener.interact(enchantment1, enchantment2)
                        if (result != ActionResult.PASS) {
                            return@EnchantmentCanCombineCallback result
                        }
                    }
                    return@EnchantmentCanCombineCallback ActionResult.PASS
                }
            }
    }
}
