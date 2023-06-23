package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * @author Enaium
 */
public class AnvilSetOutputCallbackImpl implements AnvilSetOutputCallback {
    @Override
    public void interact(ItemStack output, int levelCost, boolean canTake) {
        if (!output.equals(ItemStack.EMPTY)) {
            if (output.getNbt() != null) {
                NbtCompound nbtCompound = output.getOrCreateSubNbt(ItemStack.DISPLAY_KEY);
                nbtCompound.put(ItemStack.LORE_KEY, new NbtList());
                nbtCompound.getList(ItemStack.LORE_KEY, NbtElement.LIST_TYPE).add(NbtString.of(Text.Serializer.toJson(Text.translatable("container.repair.cost", levelCost).styled(style -> style.withBold(true).withColor(canTake ? Formatting.GREEN : Formatting.RED)))));
            }
        }
    }
}
