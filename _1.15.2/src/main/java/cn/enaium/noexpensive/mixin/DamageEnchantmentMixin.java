package cn.enaium.noexpensive.mixin;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DamageEnchantment.class)
public class DamageEnchantmentMixin extends Enchantment {


    protected DamageEnchantmentMixin(Weight weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
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