package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.LoreComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting

/**
 * @author Enaium
 */
abstract class AnvilSetOutputCallbackImpl : AnvilSetOutputCallback {
    override fun interact(output: ItemStack, levelCost: Int, canTake: Boolean, player: PlayerEntity) {
        condition(player) && return
        if (output != ItemStack.EMPTY) {
            if (output.components != null) {
                output.apply(DataComponentTypes.LORE, LoreComponent.DEFAULT) {
                    it.with(Text.translatable("container.repair.cost", levelCost).styled { style: Style ->
                        style.withBold(true).withColor(if (canTake) Formatting.GREEN else Formatting.RED)
                    })
                }
            }
        }
    }

    abstract fun condition(player: PlayerEntity): Boolean
}