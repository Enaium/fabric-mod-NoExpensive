package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback;
import net.minecraft.client.resource.language.I18n;
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
            NbtCompound nbtCompound = output.getOrCreateNbtCompound("display");
            final NbtList list = nbtCompound.getList("Lore", 8);
            for (int i = 0; i < list.size(); i++) {
                final String translate = I18n.translate("container.repair.cost", 0);
                if (list.getString(i).contains(translate.substring(0, 4))) {
                    list.remove(i);
                    break;
                }
            }
        }
    }
}
