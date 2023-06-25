package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.Config
import cn.enaium.noexpensive.ROOT
import cn.enaium.noexpensive.enums.Action
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.arguments.ItemEnchantmentArgumentType
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
                            CommandManager.argument(
                                "enchantment1",
                                ItemEnchantmentArgumentType.itemEnchantment()
                            )
                                .then(
                                    CommandManager.argument(
                                        "enchantment2",
                                        ItemEnchantmentArgumentType.itemEnchantment()
                                    ).executes { context: CommandContext<ServerCommandSource> ->
                                        val enchantment1 =
                                            ItemEnchantmentArgumentType.getEnchantment(context, "enchantment1")
                                        val enchantment2 =
                                            ItemEnchantmentArgumentType.getEnchantment(context, "enchantment2")
                                        val enchantment1Name = Registry.ENCHANTMENT.getId(enchantment1)!!.toString()
                                        val enchantment2Name = Registry.ENCHANTMENT.getId(enchantment2)!!.toString()
                                        val enchantment1Text = LiteralText(enchantment1Name).styled { style ->
                                            style.setHoverEvent(
                                                HoverEvent(
                                                    HoverEvent.Action.SHOW_TEXT,
                                                    TranslatableText(enchantment1.translationKey)
                                                )
                                            ).setColor(Formatting.AQUA)
                                        }
                                        val enchantment2Text = LiteralText(enchantment2Name).styled { style ->
                                            style.setHoverEvent(
                                                HoverEvent(
                                                    HoverEvent.Action.SHOW_TEXT,
                                                    TranslatableText(enchantment2.translationKey)
                                                )
                                            ).setColor(Formatting.AQUA)
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
                    var previous: Text? = null
                    for ((key, value) in compatibility) {
                        val enchantment = LiteralText(key).styled { style ->
                            style.setHoverEvent(
                                HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT,
                                    TranslatableText(
                                        Registry.ENCHANTMENT[Identifier(key)]!!.translationKey
                                    )
                                )
                            ).setColor(Formatting.AQUA)
                        }
                        if (!value.isEmpty()) {
                            enchantment.append(LiteralText(" -> ").styled { style -> style.setColor(Formatting.YELLOW) })
                            for (s in value) {
                                enchantment.append(LiteralText(s).styled { style ->
                                    style.setHoverEvent(
                                        HoverEvent(
                                            HoverEvent.Action.SHOW_TEXT,
                                            TranslatableText(
                                                Registry.ENCHANTMENT[Identifier(key)]!!.translationKey
                                            )
                                        )
                                    ).setColor(Formatting.AQUA)
                                })
                                if (s != value[value.size - 1]) {
                                    enchantment.append(LiteralText(", ").styled { style -> style.setColor(Formatting.YELLOW) })
                                }
                            }
                        }
                        if (previous == null) {
                            previous = enchantment
                        } else {
                            previous.append(LiteralText(", ").styled { style -> style.setColor(Formatting.RED) })
                            previous.append(enchantment)
                        }
                    }
                    val finalPrevious = previous
                    context.source.sendFeedback(finalPrevious, false)
                    Command.SINGLE_SUCCESS
                })
        )
    )
}
