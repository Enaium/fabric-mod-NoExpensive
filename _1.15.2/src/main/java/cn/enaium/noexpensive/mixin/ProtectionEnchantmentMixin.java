package cn.enaium.noexpensive.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Enaium
 */
@Mixin(ProtectionEnchantment.class)
public class ProtectionEnchantmentMixin extends Enchantment {
    protected ProtectionEnchantmentMixin(Weight weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    /**
     * @author Enaium
     * @reason no reason
     */
    @Overwrite
    public boolean differs(Enchantment other) {
        return super.differs(other);
    }
}
