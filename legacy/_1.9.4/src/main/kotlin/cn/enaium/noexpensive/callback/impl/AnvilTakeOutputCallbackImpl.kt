package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.resource.language.I18n
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack


/**
 * @author Enaium
 */
class AnvilTakeOutputCallbackImpl : AnvilTakeOutputCallback {
    override fun interact(output: ItemStack?, player: PlayerEntity) {
        MinecraftClient.getInstance().player == player || player.abilities.creativeMode && return
        if (output != null) {
            val nbtCompound = output.getSubNbt("display", true)
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
}
