package cn.enaium.noexpensive.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
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
