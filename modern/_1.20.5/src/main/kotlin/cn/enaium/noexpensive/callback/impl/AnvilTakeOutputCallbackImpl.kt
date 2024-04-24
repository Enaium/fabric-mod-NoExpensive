package cn.enaium.noexpensive.callback.impl

import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.LoreComponent
import net.minecraft.item.ItemStack

/**
 * @author Enaium
 */
class AnvilTakeOutputCallbackImpl : AnvilTakeOutputCallback {
    override fun interact(output: ItemStack) {
        val lore = output[DataComponentTypes.LORE]
        lore ?: return

        output.apply(DataComponentTypes.LORE, LoreComponent.DEFAULT) {
            LoreComponent(lore.lines.filterNot { it.toString().contains("container.repair.cost") })
        }
    }
}
