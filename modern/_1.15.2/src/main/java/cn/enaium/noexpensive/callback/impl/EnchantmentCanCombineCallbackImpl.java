package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Enaium
 */
public class EnchantmentCanCombineCallbackImpl implements EnchantmentCanCombineCallback {
    @Override
    public ActionResult interact(Enchantment enchantment1, Enchantment enchantment2) {
        final String enchantment1Name = Objects.requireNonNull(Registry.ENCHANTMENT.getId(enchantment1)).toString();
        final String enchantment2Name = Objects.requireNonNull(Registry.ENCHANTMENT.getId(enchantment2)).toString();

        final Map<String, List<String>> compatibility = Config.getModel().compatibility;

        if (compatibility.containsKey(enchantment1Name) && compatibility.get(enchantment1Name).contains(enchantment2Name)) {
            return ActionResult.PASS;
        } else if (compatibility.containsKey(enchantment2Name) && compatibility.get(enchantment2Name).contains(enchantment1Name)) {
            return ActionResult.PASS;
        }
        return enchantment1.isDifferent(enchantment2) ? ActionResult.PASS : ActionResult.FAIL;
    }
}
