package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback
import cn.enaium.noexpensive.util.getSubNbt
import net.minecraft.client.MinecraftClient
import net.minecraft.client.resource.language.I18n
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound


/**
 * @author Enaium
 */
abstract class AnvilTakeOutputCallbackImpl : AnvilTakeOutputCallback {
    override fun interact(output: ItemStack?, player: PlayerEntity) {
        MinecraftClient.getInstance().field_3805 == player || player.abilities.creativeMode && return
        if (output != null) {
            val nbtCompound: NbtCompound = getSubNbt(output.getNbt(), "display", true)!!
            val list = nbtCompound.getList("Lore", 8)
            for (i in 0 until list.size()) {
                val translate = I18n.translate("container.repair.cost", 0)
                if (list.getString(i).contains(translate.substring(0, 4))) {
                    list.remove(i)
                    break
                }
            }
        }
    }

    abstract fun condition(player: PlayerEntity): Boolean
}