package cn.enaium.noexpensive.command;

import cn.enaium.noexpensive.Config;
import cn.enaium.noexpensive.enums.Action;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EnchantmentArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

import static cn.enaium.noexpensive.NoExpensive.ROOT;

/**
 * @author Enaium
 */
public class CompatibilityCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        final LiteralArgumentBuilder<ServerCommandSource> COMPATIBILITY = CommandManager.literal("compatibility");
        for (Action action : Action.values()) {
            dispatcher.register(
                    ROOT.then(COMPATIBILITY.then(CommandManager.literal(action.name())
                                    .then(CommandManager.argument("enchantment1", EnchantmentArgumentType.enchantment())
                                            .then(CommandManager.argument("enchantment2", EnchantmentArgumentType.enchantment()).executes(context -> {

                                                final Enchantment enchantment1 = EnchantmentArgumentType.getEnchantment(context, "enchantment1");
                                                final Enchantment enchantment2 = EnchantmentArgumentType.getEnchantment(context, "enchantment2");


                                                final String enchantment1Name = Objects.requireNonNull(Registry.ENCHANTMENT.getId(enchantment1)).toString();
                                                final String enchantment2Name = Objects.requireNonNull(Registry.ENCHANTMENT.getId(enchantment2)).toString();

                                                final ItemStack enchantment1ItemStack = Items.ENCHANTED_BOOK.getDefaultStack();
                                                final ItemStack enchantment2ItemStack = Items.ENCHANTED_BOOK.getDefaultStack();
                                                enchantment1ItemStack.addEnchantment(enchantment1, 1);
                                                enchantment2ItemStack.addEnchantment(enchantment2, 1);

                                                final MutableText enchantment1Text = new LiteralText(enchantment1Name).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(enchantment1ItemStack))).withColor(Formatting.AQUA));
                                                final MutableText enchantment2Text = new LiteralText(enchantment2Name).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(enchantment2ItemStack))).withColor(Formatting.AQUA));


                                                final Map<String, List<String>> compatibility = Config.getModel().compatibility;

                                                if (action == Action.PUT) {
                                                    if (compatibility.containsKey(enchantment1Name)) {
                                                        if (!compatibility.get(enchantment1Name).contains(enchantment2Name)) {
                                                            compatibility.get(enchantment1Name).add(enchantment2Name);
                                                            context.getSource().sendFeedback(new TranslatableText("command.compatibility.put.success", enchantment1Text, enchantment2Text), false);
                                                        }
                                                    } else {
                                                        compatibility.put(enchantment1Name, new ArrayList<>(Collections.singletonList(enchantment2Name)));
                                                        context.getSource().sendFeedback(new TranslatableText("command.compatibility.put.success", enchantment1Text, enchantment2Text), false);
                                                    }                                                } else if (action == Action.REMOVE) {
                                                    if (compatibility.containsKey(enchantment1Name)) {
                                                        compatibility.get(enchantment1Name).remove(enchantment2Name);
                                                        if (compatibility.get(enchantment1Name).isEmpty()) {
                                                            compatibility.remove(enchantment1Name);
                                                        }
                                                        context.getSource().sendFeedback(new TranslatableText("command.compatibility.remove.success", enchantment1Text, enchantment2Text), false);
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
                keyItemStack.addEnchantment(Registry.ENCHANTMENT.get(new Identifier(enchantmentCompatibility.getKey())), 1);
                MutableText enchantment = new LiteralText(enchantmentCompatibility.getKey()).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(keyItemStack))).withColor(Formatting.AQUA));

                if (!enchantmentCompatibility.getValue().isEmpty()) {
                    enchantment.append(new LiteralText(" -> ").styled(style -> style.withColor(Formatting.YELLOW)));
                    for (String s : enchantmentCompatibility.getValue()) {
                        final ItemStack valueItemStack = Items.ENCHANTED_BOOK.getDefaultStack();
                        valueItemStack.addEnchantment(Registry.ENCHANTMENT.get(new Identifier(s)), 1);
                        enchantment.append(new LiteralText(s).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(valueItemStack))).withColor(Formatting.AQUA)));
                        if (!s.equals(enchantmentCompatibility.getValue().get(enchantmentCompatibility.getValue().size() - 1))) {
                            enchantment.append(new LiteralText(", ").styled(style -> style.withColor(Formatting.YELLOW)));
                        }
                    }
                }

                if (previous == null) {
                    previous = enchantment;
                } else {
                    previous.append(new LiteralText(", ").styled(style -> style.withColor(Formatting.RED)));
                    previous.append(enchantment);
                }
            }
            MutableText finalPrevious = previous;
            context.getSource().sendFeedback(finalPrevious, false);
            return Command.SINGLE_SUCCESS;
        }))));
    }
}
