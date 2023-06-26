package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.Config
import cn.enaium.noexpensive.ROOT
import cn.enaium.noexpensive.enums.Action
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.argument.EnchantmentArgumentType
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.Items
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.*
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/**
 * @author Enaium
 */
fun compatibilityCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
    val COMPATIBILITY = CommandManager.literal("compatibility")
    for (action in Action.values()) {
        dispatcher.register(
            ROOT.then(
                COMPATIBILITY.then(
                    CommandManager.literal(action.name)
                        .then(
                            CommandManager.argument<Enchantment>(
                                "enchantment1",
                                EnchantmentArgumentType.enchantment()
                            )
                                .then(
                                    CommandManager.argument<Enchantment>(
                                        "enchantment2",
                                        EnchantmentArgumentType.enchantment()
                                    ).executes { context: CommandContext<ServerCommandSource> ->
                                        val enchantment1 =
                                            EnchantmentArgumentType.getEnchantment(context, "enchantment1")
                                        val enchantment2 =
                                            EnchantmentArgumentType.getEnchantment(context, "enchantment2")
                                        val enchantment1Name = Registry.ENCHANTMENT.getId(enchantment1)!!.toString()
                                        val enchantment2Name = Registry.ENCHANTMENT.getId(enchantment2)!!.toString()
                                        val enchantment1ItemStack = Items.ENCHANTED_BOOK.defaultStack
                                        val enchantment2ItemStack = Items.ENCHANTED_BOOK.defaultStack
                                        enchantment1ItemStack.addEnchantment(enchantment1, 1)
                                        enchantment2ItemStack.addEnchantment(enchantment2, 1)
                                        val enchantment1Text =
                                            TranslatableText(enchantment1Name).styled { style: Style ->
                                                style.withHoverEvent(
                                                    HoverEvent(
                                                        HoverEvent.Action.SHOW_ITEM,
                                                        HoverEvent.ItemStackContent(enchantment1ItemStack)
                                                    )
                                                ).withColor(Formatting.AQUA)
                                            }
                                        val enchantment2Text =
                                            TranslatableText(enchantment2Name).styled { style: Style ->
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
                                                        TranslatableText(
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
                                                    TranslatableText(
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
                                                    TranslatableText(
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
                        keyItemStack.addEnchantment(Registry.ENCHANTMENT[Identifier(key)] ?: continue, 1)
                        val enchantment = LiteralText(key).styled { style: Style ->
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
                                LiteralText(" -> ").styled { style: Style -> style.withColor(Formatting.YELLOW) })
                            for (s in value) {
                                val valueItemStack = Items.ENCHANTED_BOOK.defaultStack
                                valueItemStack.addEnchantment(Registry.ENCHANTMENT[Identifier(s)], 1)
                                enchantment.append(LiteralText(s).styled { style: Style ->
                                    style.withHoverEvent(
                                        HoverEvent(
                                            HoverEvent.Action.SHOW_ITEM, HoverEvent.ItemStackContent(valueItemStack)
                                        )
                                    ).withColor(Formatting.AQUA)
                                })
                                if (s != value[value.size - 1]) {
                                    enchantment.append(
                                        LiteralText(", ")
                                            .styled { style: Style -> style.withColor(Formatting.YELLOW) })
                                }
                            }
                        }
                        if (previous == null) {
                            previous = enchantment
                        } else {
                            previous.append(
                                LiteralText(", ").styled { style: Style -> style.withColor(Formatting.RED) })
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
