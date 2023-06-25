package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.Config
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback
import net.minecraft.enchantment.Enchantment


/**
 * @author Enaium
 */
class EnchantmentCanCombineCallbackImpl : EnchantmentCanCombineCallback {
    override fun interact(enchantment1: Enchantment, enchantment2: Enchantment): Boolean {
        val compatibility: Map<Int, List<Int>> = Config.model.compatibility
        if (compatibility.containsKey(enchantment1.id) && compatibility[enchantment1.id]!!.contains(enchantment2.id)) {
            return true
        } else if (compatibility.containsKey(enchantment2.id) && compatibility[enchantment2.id]!!.contains(enchantment1.id)) {
            return true
        }
        return enchantment1.differs(enchantment2)
    }
}
