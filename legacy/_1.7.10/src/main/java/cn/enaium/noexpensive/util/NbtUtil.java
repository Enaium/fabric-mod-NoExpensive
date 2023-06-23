package cn.enaium.noexpensive.util;

import net.minecraft.nbt.NbtCompound;

/**
 * @author Enaium
 */
public class NbtUtil {
    public static NbtCompound getSubNbt(NbtCompound nbt, String name, boolean createIfNull) {
        if (nbt != null && nbt.contains(name, 10)) {
            return nbt.getCompound(name);
        } else if (createIfNull) {
            NbtCompound nbtCompound = new NbtCompound();
            assert nbt != null;
            nbt.put(name, nbtCompound);
            return nbtCompound;
        } else {
            return null;
        }
    }
}
