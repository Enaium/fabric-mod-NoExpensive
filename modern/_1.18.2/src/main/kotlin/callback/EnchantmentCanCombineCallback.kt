package callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.enchantment.Enchantment
import net.minecraft.util.ActionResult
import java.util.function.Function

/**
 * @author Enaium
 */
fun interface EnchantmentCanCombineCallback {
    fun interact(enchantment1: Enchantment, enchantment2: Enchantment): ActionResult

    companion object {
        val EVENT: Event<EnchantmentCanCombineCallback> =
            EventFactory.createArrayBacked(EnchantmentCanCombineCallback::class.java) { listeners ->
                EnchantmentCanCombineCallback { enchantment1, enchantment2 ->
                    for (listener in listeners) {
                        val result = listener!!.interact(enchantment1, enchantment2)
                        if (result != ActionResult.PASS) {
                            return@EnchantmentCanCombineCallback result
                        }
                    }
                    return@EnchantmentCanCombineCallback ActionResult.PASS
                }
            }
    }
}
