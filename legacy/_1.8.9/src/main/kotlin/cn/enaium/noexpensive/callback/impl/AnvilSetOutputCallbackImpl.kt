package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback
import net.minecraft.client.resource.language.I18n
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString


/**
 * @author Enaium
 */
abstract class AnvilSetOutputCallbackImpl : AnvilSetOutputCallback {
    override fun interact(output: ItemStack?, levelCost: Int, canTake: Boolean, player: PlayerEntity) {
        condition(player) && return
        if (output != null) {
            if (output.nbt != null) {
                val nbtCompound = output.getSubNbt("display", true)
                nbtCompound.put("Lore", NbtList())
                nbtCompound.getList("Lore", 9).add(
                    NbtString(
                        I18n.translate(
                            "container.repair.cost",
                            (if (canTake) "§a§l" else "§c§l") + levelCost
                        )
                    )
                )
            }
        }
    }

    abstract fun condition(player: PlayerEntity): Boolean
}