package cn.enaium.noexpensive.callback.impl;

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
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
                CompoundTag compoundTag = output.getOrCreateSubTag("display");
                compoundTag.put("Lore", new ListTag());
                compoundTag.getList("Lore", 9).add(StringTag.of(Text.Serializer.toJson(new TranslatableText("container.repair.cost", levelCost).styled(style -> style.setBold(true).setColor(canTake ? Formatting.GREEN : Formatting.RED)))));
            }
        }
    }
}
