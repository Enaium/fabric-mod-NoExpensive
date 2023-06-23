package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback;
import cn.enaium.noexpensive.mixin.EnchantmentMixin;
import net.minecraft.enchantment.Enchantment;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Enaium
 */
public class EnchantmentCanCombineCallbackImpl implements EnchantmentCanCombineCallback {
    @Override
    public boolean interact(Enchantment enchantment1, Enchantment enchantment2) {
        final String enchantment1Name = Objects.requireNonNull((getKeyByValue(((EnchantmentMixin) enchantment1).getENCHANTMENT_MAP(), enchantment1))).toString();
        final String enchantment2Name = Objects.requireNonNull((getKeyByValue(((EnchantmentMixin) enchantment2).getENCHANTMENT_MAP(), enchantment2))).toString();

        final Map<String, List<String>> compatibility = Config.getModel().compatibility;

        if (compatibility.containsKey(enchantment1Name) && compatibility.get(enchantment1Name).contains(enchantment2Name)) {
            return true;
        } else if (compatibility.containsKey(enchantment2Name) && compatibility.get(enchantment2Name).contains(enchantment1Name)) {
            return true;
        }
        return enchantment1.differs(enchantment2);
    }

    private static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
