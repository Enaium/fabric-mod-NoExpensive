package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.Config
import cn.enaium.noexpensive.ROOT
import cn.enaium.noexpensive.enums.Action
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.RegistryEntryArgumentType
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.HoverEvent
import net.minecraft.text.MutableText
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

/**
 * @author Enaium
 */
fun compatibilityCommand(dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess) {
    val COMPATIBILITY = CommandManager.literal("compatibility")
    for (action in Action.values()) {
        dispatcher.register(
            ROOT.then(
                COMPATIBILITY.then(
                    CommandManager.literal(action.name)
                        .then(
                            CommandManager.argument<RegistryEntry.Reference<Enchantment>>(
                                "enchantment1",
                                RegistryEntryArgumentType.registryEntry<Enchantment>(
                                    registryAccess,
                                    RegistryKeys.ENCHANTMENT
                                )
                            )
                                .then(
                                    CommandManager.argument<RegistryEntry.Reference<Enchantment>>(
                                        "enchantment2",
                                        RegistryEntryArgumentType.registryEntry<Enchantment>(
                                            registryAccess,
                                            RegistryKeys.ENCHANTMENT
                                        )
                                    ).executes { context: CommandContext<ServerCommandSource> ->
                                        val enchantment1 =
                                            RegistryEntryArgumentType.getEnchantment(context, "enchantment1")
                                        val enchantment2 =
                                            RegistryEntryArgumentType.getEnchantment(context, "enchantment2")
                                        val enchantment1Name = enchantment1.registryKey().value.toString()
                                        val enchantment2Name = enchantment2.registryKey().value.toString()
                                        val enchantment1ItemStack = Items.ENCHANTED_BOOK.defaultStack
                                        val enchantment2ItemStack = Items.ENCHANTED_BOOK.defaultStack
                                        enchantment1ItemStack.addEnchantment(enchantment1.value(), 1)
                                        enchantment2ItemStack.addEnchantment(enchantment2.value(), 1)
                                        val enchantment1Text =
                                            Text.literal(enchantment1Name).styled { style: Style ->
                                                style.withHoverEvent(
                                                    HoverEvent(
                                                        HoverEvent.Action.SHOW_ITEM,
                                                        HoverEvent.ItemStackContent(enchantment1ItemStack)
                                                    )
                                                ).withColor(Formatting.AQUA)
                                            }
                                        val enchantment2Text =
                                            Text.literal(enchantment2Name).styled { style: Style ->
                                                style.withHoverEvent(
                                                    HoverEvent(
                                                        HoverEvent.Action.SHOW_ITEM,
                                                        HoverEvent.ItemStackContent(enchantment2ItemStack)
                                                    )
                                                ).withColor(Formatting.AQUA)
                                            }
                                        val compatibility = Config.model.compatibility
                                        if (action === Action.PUT) {
                                            if (compatibility.containsKey(enchantment1Name)) {
                                                if (!compatibility[enchantment1Name]!!.contains(enchantment2Name)) {
                                                    compatibility[enchantment1Name]!!.add(enchantment2Name)
                                                    context.source.sendFeedback(
                                                        Text.translatable(
                                                            "command.compatibility.put.success",
                                                            enchantment1Text,
                                                            enchantment2Text
                                                        ), false
                                                    )
                                                }
                                            } else {
                                                compatibility[enchantment1Name] =
                                                    ArrayList(listOf(enchantment2Name))
                                                context.source.sendFeedback(
                                                    Text.translatable(
                                                        "command.compatibility.put.success",
                                                        enchantment1Text,
                                                        enchantment2Text
                                                    ), false
                                                )
                                            }
                                        } else if (action === Action.REMOVE) {
                                            if (compatibility.containsKey(enchantment1Name)) {
                                                compatibility[enchantment1Name]!!.remove(enchantment2Name)
                                                if (compatibility[enchantment1Name]!!.isEmpty()) {
                                                    compatibility.remove(enchantment1Name)
                                                }
                                                context.source.sendFeedback(
                                                    Text.translatable(
                                                        "command.compatibility.remove.success",
                                                        enchantment1Text,
                                                        enchantment2Text
                                                    ), false
                                                )
                                            }
                                        }
                                        Config.save()
                                        Command.SINGLE_SUCCESS
                                    })
                        )
                )
            )
        )
    }
    dispatcher.register(
        ROOT.then(
            COMPATIBILITY.then(
                CommandManager.literal("list").executes { context: CommandContext<ServerCommandSource> ->
                    val compatibility = Config.model.compatibility
                    var previous: MutableText? = null
                    for ((key, value) in compatibility) {
                        val keyItemStack = Items.ENCHANTED_BOOK.defaultStack
                        keyItemStack.addEnchantment(Registries.ENCHANTMENT[Identifier(key)] ?: continue, 1)
                        val enchantment = Text.literal(key).styled { style: Style ->
                            style.withHoverEvent(
                                HoverEvent(
                                    HoverEvent.Action.SHOW_ITEM,
                                    HoverEvent.ItemStackContent(keyItemStack)
                                )
                            ).withColor(
                                Formatting.AQUA
                            )
                        }
                        if (value.isNotEmpty()) {
                            enchantment.append(
                                Text.literal(" -> ").styled { style: Style -> style.withColor(Formatting.YELLOW) })
                            for (s in value) {
                                val valueItemStack = Items.ENCHANTED_BOOK.defaultStack
                                valueItemStack.addEnchantment(Registries.ENCHANTMENT[Identifier(s)], 1)
                                enchantment.append(Text.literal(s).styled { style: Style ->
                                    style.withHoverEvent(
                                        HoverEvent(
                                            HoverEvent.Action.SHOW_ITEM, HoverEvent.ItemStackContent(valueItemStack)
                                        )
                                    ).withColor(Formatting.AQUA)
                                })
                                if (s != value[value.size - 1]) {
                                    enchantment.append(
                                        Text.literal(", ")
                                            .styled { style: Style -> style.withColor(Formatting.YELLOW) })
                                }
                            }
                        }
                        if (previous == null) {
                            previous = enchantment
                        } else {
                            previous.append(
                                Text.literal(", ").styled { style: Style -> style.withColor(Formatting.RED) })
                            previous.append(enchantment)
                        }
                    }
                    if (previous != null) {
                        context.source.sendFeedback(previous, false)
                    }
                    Command.SINGLE_SUCCESS
                })
        )
    )
}
