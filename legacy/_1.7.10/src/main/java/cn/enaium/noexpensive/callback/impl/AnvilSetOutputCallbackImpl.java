package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import static cn.enaium.noexpensive.util.NbtUtil.getSubNbt;

/**
 * @author Enaium
 */
public class AnvilSetOutputCallbackImpl implements AnvilSetOutputCallback {
    @Override
    public void interact(ItemStack output, int levelCost, boolean canTake) {
        if (output != null) {
            if (output.getNbt() != null) {
                NbtCompound nbtCompound = getSubNbt(output.getNbt(), "display", true);
                nbtCompound.put("Lore", new NbtList());
                nbtCompound.getList("Lore", 9).add(new NbtString(I18n.translate("container.repair.cost", (canTake ? "§a§l" : "§c§l") + levelCost)));
            }
        }
    }
}
