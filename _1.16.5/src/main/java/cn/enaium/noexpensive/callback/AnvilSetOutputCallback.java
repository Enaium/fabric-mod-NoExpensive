package cn.enaium.noexpensive.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;

/**
 * @author Enaium
 */
public interface AnvilSetOutputCallback {
    Event<AnvilSetOutputCallback> EVENT = EventFactory.createArrayBacked(AnvilSetOutputCallback.class, (listeners) -> (output, levelCost, canTake) -> {
        for (AnvilSetOutputCallback listener : listeners) {
            listener.interact(output, levelCost, canTake);
        }
    });

    void interact(ItemStack output, int levelCost, boolean canTake);
}
