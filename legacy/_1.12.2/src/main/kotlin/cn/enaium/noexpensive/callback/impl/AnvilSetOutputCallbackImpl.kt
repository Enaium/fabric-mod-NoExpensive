package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.resource.language.I18n
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString

/**
 * @author Enaium
 */
class AnvilSetOutputCallbackImpl : AnvilSetOutputCallback {
    override fun interact(output: ItemStack, levelCost: Int, canTake: Boolean, player: PlayerEntity) {
        MinecraftClient.getInstance().player == player || player.abilities.creativeMode && return
        if (!output.equals(ItemStack.EMPTY)) {
            if (output.nbt != null) {
                val nbtCompound = output.getOrCreateNbtCompound("display")
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
}
