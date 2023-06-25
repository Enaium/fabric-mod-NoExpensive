package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.Config
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback
import net.minecraft.enchantment.Enchantment
import net.minecraft.util.ActionResult
import net.minecraft.util.registry.Registry
import java.util.*

/**
 * @author Enaium
 */
class EnchantmentCanCombineCallbackImpl : EnchantmentCanCombineCallback {
    override fun interact(enchantment1: Enchantment, enchantment2: Enchantment): ActionResult {
        val enchantment1Name = Objects.requireNonNull(Registry.ENCHANTMENT.getId(enchantment1)).toString()
        val enchantment2Name = Objects.requireNonNull(Registry.ENCHANTMENT.getId(enchantment2)).toString()
        val compatibility: Map<String, List<String>> = Config.model.compatibility
        if (compatibility.containsKey(enchantment1Name) && compatibility[enchantment1Name]!!.contains(enchantment2Name)) {
            return ActionResult.PASS
        } else if (compatibility.containsKey(enchantment2Name) && compatibility[enchantment2Name]!!.contains(
                enchantment1Name
            )
        ) {
            return ActionResult.PASS
        }
        return if (enchantment1.isDifferent(enchantment2)) ActionResult.PASS else ActionResult.FAIL
    }
}
