package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.StringTag
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
                val compoundTag = output.getOrCreateSubTag("display")
                compoundTag.put("Lore", ListTag())
                compoundTag.getList("Lore", 9).add(
                    StringTag(
                        Text.Serializer.toJson(
                            TranslatableText("container.repair.cost", levelCost).styled { style: Style ->
                                style.setBold(true).setColor(if (canTake) Formatting.GREEN else Formatting.RED)
                            })
                    )
                )
            }
        }
    }

    abstract fun condition(player: PlayerEntity): Boolean
}