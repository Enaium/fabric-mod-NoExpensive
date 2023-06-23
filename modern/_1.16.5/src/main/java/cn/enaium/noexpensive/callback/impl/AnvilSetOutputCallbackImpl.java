package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

/**
 * @author Enaium
 */
public class AnvilSetOutputCallbackImpl implements AnvilSetOutputCallback {
    @Override
    public void interact(ItemStack output, int levelCost, boolean canTake) {
        if (!output.equals(ItemStack.EMPTY)) {
            if (output.getTag() != null) {
                NbtCompound nbtCompound = output.getOrCreateSubTag("display");
                nbtCompound.put("Lore", new NbtList());
                nbtCompound.getList("Lore", 9).add(NbtString.of(Text.Serializer.toJson(new TranslatableText("container.repair.cost", levelCost).styled(style -> style.withBold(true).withColor(canTake ? Formatting.GREEN : Formatting.RED)))));
            }
        }
    }
}
