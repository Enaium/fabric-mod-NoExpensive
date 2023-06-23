package cn.enaium.noexpensive.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * @author Enaium
 */
@Mixin(Enchantment.class)
public interface EnchantmentMixin {
    @Accessor
    Map<Identifier, Enchantment> getENCHANTMENT_MAP();
}
