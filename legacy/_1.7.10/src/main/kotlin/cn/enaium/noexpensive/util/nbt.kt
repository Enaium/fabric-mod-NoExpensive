package cn.enaium.noexpensive.util

import net.minecraft.nbt.NbtCompound

/**
 * @author Enaium
 */
fun getSubNbt(nbt: NbtCompound?, name: String?, createIfNull: Boolean): NbtCompound? {
    return if (nbt != null && nbt.contains(name, 10)) {
        nbt.getCompound(name)
    } else if (createIfNull) {
        val nbtCompound = NbtCompound()
        assert(nbt != null)
        nbt!!.put(name, nbtCompound)
        nbtCompound
    } else {
        null
    }
}