/*
 * Copyright (c) 2026 Enaium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.ROOT
import cn.enaium.noexpensive.common.Action
import cn.enaium.noexpensive.event.ServerCommandCallbacks
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.argument.EnchantmentArgumentType
import net.minecraft.item.Items
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.HoverEvent
import net.minecraft.text.LiteralText
import net.minecraft.text.Style
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.registry.Registry

/**
 * @author Enaium
 */
fun action(dispatcher: CommandDispatcher<ServerCommandSource>) {
    val COMPATIBILITY = CommandManager.literal("compatibility")
    for (action in Action.entries) {
        dispatcher.register(
            ROOT.then(
                COMPATIBILITY.then(
                    CommandManager.literal(action.name)
                        .then(
                            CommandManager.argument(
                                "enchantment1",
                                EnchantmentArgumentType.enchantment()
                            )
                                .then(
                                    CommandManager.argument(
                                        "enchantment2",
                                        EnchantmentArgumentType.enchantment()
                                    ).executes { context: CommandContext<ServerCommandSource> ->
                                        val enchantment =
                                            EnchantmentArgumentType.getEnchantment(context, "enchantment1")
                                        val other =
                                            EnchantmentArgumentType.getEnchantment(context, "enchantment2")
                                        val enchantmentName = Registry.ENCHANTMENT.getId(enchantment)!!.toString()
                                        val otherName = Registry.ENCHANTMENT.getId(other)!!.toString()
                                        val enchantmentItemStack = Items.ENCHANTED_BOOK.defaultStack
                                        val otherItemStack = Items.ENCHANTED_BOOK.defaultStack
                                        enchantmentItemStack.addEnchantment(enchantment, 1)
                                        otherItemStack.addEnchantment(other, 1)
                                        val enchantmentText = LiteralText(enchantmentName).styled { style: Style ->
                                            style.withHoverEvent(
                                                HoverEvent(
                                                    HoverEvent.Action.SHOW_ITEM,
                                                    HoverEvent.ItemStackContent(enchantmentItemStack)
                                                )
                                            ).withColor(Formatting.AQUA)
                                        }
                                        val otherText =
                                            LiteralText(otherName).styled { style: Style ->
                                                style.withHoverEvent(
                                                    HoverEvent(
                                                        HoverEvent.Action.SHOW_ITEM,
                                                        HoverEvent.ItemStackContent(otherItemStack)
                                                    )
                                                ).withColor(Formatting.AQUA)
                                            }
                                        if (ServerCommandCallbacks.ActionCallback.EVENT.invoker.execute(
                                                action,
                                                otherName,
                                                otherName
                                            )
                                        ) {
                                            context.source.sendFeedback(
                                                TranslatableText(
                                                    when (action) {
                                                        Action.PUT -> "command.compatibility.put.success"
                                                        Action.REMOVE -> "command.compatibility.remove.success"
                                                    },
                                                    enchantmentText,
                                                    otherText
                                                ), false
                                            )
                                        }
                                        Command.SINGLE_SUCCESS
                                    })
                        )
                )
            )
        )
    }
}
