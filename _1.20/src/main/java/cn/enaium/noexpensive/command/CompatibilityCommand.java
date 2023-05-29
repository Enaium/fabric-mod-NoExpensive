package cn.enaium.noexpensive.command;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.enums.Action;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.enaium.noexpensive.NoExpensive.ROOT;

/**
 * @author Enaium
 */
public class CompatibilityCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        final LiteralArgumentBuilder<ServerCommandSource> COMPATIBILITY = CommandManager.literal("compatibility");
        for (Action action : Action.values()) {
            dispatcher.register(
                    ROOT.then(COMPATIBILITY.then(CommandManager.literal(action.name())
                                    .then(CommandManager.argument("enchantment1", RegistryEntryArgumentType.registryEntry(registryAccess, RegistryKeys.ENCHANTMENT))
                                            .then(CommandManager.argument("enchantment2", RegistryEntryArgumentType.registryEntry(registryAccess, RegistryKeys.ENCHANTMENT)).executes(context -> {

                                                final RegistryEntry.Reference<Enchantment> enchantment1 = RegistryEntryArgumentType.getEnchantment(context, "enchantment1");
                                                final RegistryEntry.Reference<Enchantment> enchantment2 = RegistryEntryArgumentType.getEnchantment(context, "enchantment2");

                                                final String enchantment1Name = enchantment1.registryKey().getValue().toString();
                                                final String enchantment2Name = enchantment2.registryKey().getValue().toString();

                                                final ItemStack enchantment1ItemStack = Items.ENCHANTED_BOOK.getDefaultStack();
                                                final ItemStack enchantment2ItemStack = Items.ENCHANTED_BOOK.getDefaultStack();
                                                enchantment1ItemStack.addEnchantment(enchantment1.value(), 1);
                                                enchantment2ItemStack.addEnchantment(enchantment2.value(), 1);

                                                final MutableText enchantment1Text = Text.literal(enchantment1Name).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(enchantment1ItemStack))).withColor(Formatting.AQUA));
                                                final MutableText enchantment2Text = Text.literal(enchantment2Name).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(enchantment2ItemStack))).withColor(Formatting.AQUA));


                                                final Map<String, List<String>> compatibility = Config.getModel().compatibility;

                                                if (action == Action.PUT) {
                                                    if (compatibility.containsKey(enchantment1Name)) {
                                                        compatibility.get(enchantment1Name).add(enchantment2Name);
                                                    } else {
                                                        compatibility.put(enchantment1Name, Collections.singletonList(enchantment2Name));
                                                    }
                                                    context.getSource().sendFeedback(() -> Text.translatable("command.compatibility.put.success", enchantment1Text, enchantment2Text), false);
                                                } else if (action == Action.REMOVE) {
                                                    if (compatibility.containsKey(enchantment1Name)) {
                                                        compatibility.get(enchantment1Name).remove(enchantment2Name);
                                                        context.getSource().sendFeedback(() -> Text.translatable("command.compatibility.remove.success", enchantment1Text, enchantment2Text), false);
                                                    }
                                                }

                                                Config.save();
                                                return Command.SINGLE_SUCCESS;
                                            }))
                                    )
                            )
                    ));
        }
        dispatcher.register(ROOT.then(COMPATIBILITY.then(CommandManager.literal("list").executes(context -> {
            final Map<String, List<String>> compatibility = Config.getModel().compatibility;
            MutableText previous = null;
            for (Map.Entry<String, List<String>> enchantmentCompatibility : compatibility.entrySet()) {

                final ItemStack keyItemStack = Items.ENCHANTED_BOOK.getDefaultStack();
                keyItemStack.addEnchantment(Registries.ENCHANTMENT.get(new Identifier(enchantmentCompatibility.getKey())), 1);
                MutableText enchantment = Text.literal(enchantmentCompatibility.getKey()).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(keyItemStack))).withColor(Formatting.AQUA));

                if (!enchantmentCompatibility.getValue().isEmpty()) {
                    enchantment.append(Text.literal(" -> ").styled(style -> style.withColor(Formatting.YELLOW)));
                    for (String s : enchantmentCompatibility.getValue()) {
                        final ItemStack valueItemStack = Items.ENCHANTED_BOOK.getDefaultStack();
                        valueItemStack.addEnchantment(Registries.ENCHANTMENT.get(new Identifier(s)), 1);
                        enchantment.append(Text.literal(s).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(valueItemStack))).withColor(Formatting.AQUA)));
                    }
                }

                if (previous == null) {
                    previous = enchantment;
                } else {
                    previous.append(Text.literal(", ").styled(style -> style.withColor(Formatting.RED)));
                    previous.append(enchantment);
                }
            }
            MutableText finalPrevious = previous;
            context.getSource().sendFeedback(() -> finalPrevious, false);
            return Command.SINGLE_SUCCESS;
        }))));
    }
}
