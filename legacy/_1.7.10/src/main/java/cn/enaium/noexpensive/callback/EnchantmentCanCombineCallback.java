package cn.enaium.noexpensive.callback;

import net.legacyfabric.fabric.api.event.Event;
import net.legacyfabric.fabric.api.event.EventFactory;
import net.minecraft.enchantment.Enchantment;

/**
 * @author Enaium
 */
public interface EnchantmentCanCombineCallback {
    Event<EnchantmentCanCombineCallback> EVENT = EventFactory.createArrayBacked(EnchantmentCanCombineCallback.class, (listeners) -> (enchantment1, enchantment2) -> {
        for (EnchantmentCanCombineCallback listener : listeners) {
            boolean result = listener.interact(enchantment1, enchantment2);
            if (!result) {
                return false;
            }
        }

        return true;
    });

    boolean interact(Enchantment enchantment1, Enchantment enchantment2);
}
