package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.Config
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback
import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.util.ActionResult

/**
 * @author Enaium
 */
class EnchantmentCanCombineCallbackImpl : EnchantmentCanCombineCallback {
    override fun interact(
        enchantment1: RegistryEntry<Enchantment>,
        enchantment2: RegistryEntry<Enchantment>
    ): ActionResult {
        val enchantment1Name = enchantment1.idAsString
        val enchantment2Name = enchantment2.idAsString
        val compatibility: Map<String, List<String>> = Config.model.compatibility
        if (compatibility.containsKey(enchantment1Name) && compatibility[enchantment1Name]!!.contains(enchantment2Name)) {
            return ActionResult.PASS
        } else if (compatibility.containsKey(enchantment2Name) && compatibility[enchantment2Name]!!.contains(
                enchantment1Name
            )
        ) {
            return ActionResult.PASS
        }
        return if (Enchantment.canBeCombined(enchantment1, enchantment2)) ActionResult.PASS else ActionResult.FAIL
    }
}
