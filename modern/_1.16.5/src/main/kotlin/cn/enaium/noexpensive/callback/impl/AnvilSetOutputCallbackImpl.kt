package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting

/**
 * @author Enaium
 */
abstract class AnvilSetOutputCallbackImpl : AnvilSetOutputCallback {
    override fun interact(output: ItemStack, levelCost: Int, canTake: Boolean, player: PlayerEntity) {
        condition(player) && return
        if (output != ItemStack.EMPTY) {
            if (output.tag != null) {
                val nbtCompound = output.getOrCreateSubTag("display")
                nbtCompound.put("Lore", NbtList())
                nbtCompound.getList("Lore", 9).add(
                    NbtString.of(
                        Text.Serializer.toJson(
                            TranslatableText("container.repair.cost", levelCost).styled { style: Style ->
                                style.withBold(true).withColor(if (canTake) Formatting.GREEN else Formatting.RED)
                            })
                    )
                )
            }
        }
    }

    abstract fun condition(player: PlayerEntity): Boolean
}