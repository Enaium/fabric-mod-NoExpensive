package cn.enaium.noexpensive.callback;

import net.legacyfabric.fabric.api.event.Event;
import net.legacyfabric.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;

/**
 * @author Enaium
 */
public interface AnvilTakeOutputCallback {
    Event<AnvilTakeOutputCallback> EVENT = EventFactory.createArrayBacked(AnvilTakeOutputCallback.class, (listeners) -> (output) -> {
        for (AnvilTakeOutputCallback listener : listeners) {
            listener.interact(output);
        }
    });

    void interact(ItemStack output);
}
