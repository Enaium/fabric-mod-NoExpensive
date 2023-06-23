package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback;
import net.minecraft.enchantment.Enchantment;

import java.util.List;
import java.util.Map;

/**
 * @author Enaium
 */
public class EnchantmentCanCombineCallbackImpl implements EnchantmentCanCombineCallback {
    @Override
    public boolean interact(Enchantment enchantment1, Enchantment enchantment2) {
        final Map<Integer, List<Integer>> compatibility = Config.getModel().compatibility;

        if (compatibility.containsKey(enchantment1.id) && compatibility.get(enchantment1.id).contains(enchantment2.id)) {
            return true;
        } else if (compatibility.containsKey(enchantment2.id) && compatibility.get(enchantment2.id).contains(enchantment1.id)) {
            return true;
        }
        return enchantment1.differs(enchantment2);
    }
}
