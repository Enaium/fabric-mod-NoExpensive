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

import cn.enaium.noexpensive.Config.model
import cn.enaium.noexpensive.ROOT
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.resources.Identifier

/**
 * @author Enaium
 */
fun list(dispatcher: CommandDispatcher<CommandSourceStack>) {
    val COMPATIBILITY = Commands.literal("compatibility")
    dispatcher.register(
        ROOT.then(
            COMPATIBILITY.then(
                Commands.literal("list").executes { context: CommandContext<CommandSourceStack> ->
                    val compatibility = model.compatibility
                    var previous: MutableComponent? = null
                    for ((key, value) in compatibility) {
                        val enchantment = Component.literal(key).withStyle { style: Style ->
                            style.withHoverEvent(
                                HoverEvent.ShowText(
                                    Component.translatable(Identifier.parse(key).toLanguageKey("enchantment"))
                                )
                            ).withColor(
                                ChatFormatting.AQUA
                            )
                        }
                        if (value.isNotEmpty()) {
                            enchantment.append(
                                Component.literal(" -> ")
                                    .withStyle { style: Style -> style.withColor(ChatFormatting.YELLOW) })
                            for (s in value) {
                                enchantment.append(Component.literal(s).withStyle { style: Style ->
                                    style.withHoverEvent(
                                        HoverEvent.ShowText(
                                            Component.translatable(Identifier.parse(s).toLanguageKey("enchantment"))
                                        )
                                    ).withColor(ChatFormatting.AQUA)
                                })
                                if (s != value[value.size - 1]) {
                                    enchantment.append(
                                        Component.literal(", ")
                                            .withStyle { style: Style -> style.withColor(ChatFormatting.YELLOW) })
                                }
                            }
                        }
                        if (previous == null) {
                            previous = enchantment
                        } else {
                            previous.append(
                                Component.literal(", ")
                                    .withStyle { style: Style -> style.withColor(ChatFormatting.RED) })
                            previous.append(enchantment)
                        }
                    }

                    if (previous != null) {
                        context.source.sendSystemMessage(previous)
                    }
                    Command.SINGLE_SUCCESS
                })
        )
    )
}
