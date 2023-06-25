package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback
import cn.enaium.noexpensive.mixin.EnchantmentMixin
import net.minecraft.enchantment.Enchantment
import java.util.*


/**
 * @author Enaium
 */
class EnchantmentCanCombineCallbackImpl : EnchantmentCanCombineCallback {
    override fun interact(enchantment1: Enchantment, enchantment2: Enchantment): Boolean {
        val enchantment1Name: String = (enchantment1 as EnchantmentMixin).enchantmenT_MAP.entries.find { it.value == enchantment1 }?.key.toString()
        val enchantment2Name: String = (enchantment2 as EnchantmentMixin).enchantmenT_MAP.entries.find { it.value == enchantment2 }?.key.toString()
        val compatibility: Map<String, List<String>> = Config.model.compatibility
        if (compatibility.containsKey(enchantment1Name) && compatibility[enchantment1Name]!!.contains(enchantment2Name)) {
            return true
        } else if (compatibility.containsKey(enchantment2Name) && compatibility[enchantment2Name]!!.contains(
                enchantment1Name
            )
        ) {
            return true
        }
        return enchantment1.differs(enchantment2)
    }
}
