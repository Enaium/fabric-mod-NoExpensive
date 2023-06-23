package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

/**
 * @author Enaium
 */
public class AnvilTakeOutputCallbackImpl implements AnvilTakeOutputCallback {
    @Override
    public void interact(ItemStack output) {
        if (!output.equals(ItemStack.EMPTY)) {
            CompoundTag nbtCompound = output.getOrCreateSubTag("display");
            final ListTag list = nbtCompound.getList("Lore", 8);
            for (int i = 0; i < list.size(); i++) {
                if (list.getString(i).contains("container.repair.cost")) {
                    list.remove(i);
                    break;
                }
            }
        }
    }
}
