package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback
import cn.enaium.noexpensive.util.getSubNbt
import net.minecraft.client.MinecraftClient
import net.minecraft.client.resource.language.I18n
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString


/**
 * @author Enaium
 */
abstract class AnvilSetOutputCallbackImpl : AnvilSetOutputCallback {
    override fun interact(output: ItemStack?, levelCost: Int, canTake: Boolean, player: PlayerEntity) {
        MinecraftClient.getInstance().field_3805 == player || player.abilities.creativeMode && return
        if (output != null) {
            if (output.getNbt() != null) {
                val nbtCompound: NbtCompound = getSubNbt(output.getNbt(), "display", true)!!
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