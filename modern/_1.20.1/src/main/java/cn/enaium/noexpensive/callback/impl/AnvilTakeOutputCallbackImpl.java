package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

/**
 * @author Enaium
 */
public class AnvilTakeOutputCallbackImpl implements AnvilTakeOutputCallback {
    @Override
    public void interact(ItemStack output) {
        NbtCompound nbtCompound = output.getOrCreateSubNbt(ItemStack.DISPLAY_KEY);
        final NbtList list = nbtCompound.getList(ItemStack.LORE_KEY, NbtElement.STRING_TYPE);
        for (int i = 0; i < list.size(); i++) {
            if (list.getString(i).contains("container.repair.cost")) {
                list.remove(i);
                break;
            }
        }
    }
}
