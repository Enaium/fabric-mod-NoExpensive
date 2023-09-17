package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtElement

/**
 * @author Enaium
 */
class AnvilTakeOutputCallbackImpl : AnvilTakeOutputCallback {
    override fun interact(output: ItemStack) {
        val nbtCompound = output.getOrCreateSubNbt(ItemStack.DISPLAY_KEY)
        val list = nbtCompound.getList(ItemStack.LORE_KEY, NbtElement.STRING_TYPE.toInt())
        for (i in list.indices) {
            if (list.getString(i).contains("container.repair.cost")) {
                list.removeAt(i)
                break
            }
        }
    }
}
