package cn.enaium.noexpensive.callback;

import net.legacyfabric.fabric.api.event.Event;
import net.legacyfabric.fabric.api.event.EventFactory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ActionResult;

/**
 * @author Enaium
 */
public interface EnchantmentCanCombineCallback {
    Event<EnchantmentCanCombineCallback> EVENT = EventFactory.createArrayBacked(EnchantmentCanCombineCallback.class, (listeners) -> (enchantment1, enchantment2) -> {
        for (EnchantmentCanCombineCallback listener : listeners) {
            ActionResult result = listener.interact(enchantment1, enchantment2);
            if (result != ActionResult.PASS) {
                return result;
            }
        }

        return ActionResult.PASS;
    });

    ActionResult interact(Enchantment enchantment1, Enchantment enchantment2);
}
