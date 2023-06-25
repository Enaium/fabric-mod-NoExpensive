package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback
import net.minecraft.item.ItemStack

/**
 * @author Enaium
 */
class AnvilTakeOutputCallbackImpl : AnvilTakeOutputCallback {
    override fun interact(output: ItemStack) {
        val compoundTag = output.getOrCreateSubTag("display")
        val list = compoundTag.getList("Lore", 8)
        for (i in list.indices) {
            if (list.getString(i).contains("container.repair.cost")) {
                list.removeAt(i)
                break
            }
        }
    }
}
