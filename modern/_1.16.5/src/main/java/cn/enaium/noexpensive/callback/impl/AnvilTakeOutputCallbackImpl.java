package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

/**
 * @author Enaium
 */
public class AnvilTakeOutputCallbackImpl implements AnvilTakeOutputCallback {
    @Override
    public void interact(ItemStack output) {
        if (!output.equals(ItemStack.EMPTY)) {
            NbtCompound nbtCompound = output.getOrCreateSubTag("display");
            final NbtList list = nbtCompound.getList("Lore", 8);
            for (int i = 0; i < list.size(); i++) {
                if (list.getString(i).contains("container.repair.cost")) {
                    list.remove(i);
                    break;
                }
            }
        }
    }
}
