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
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.HoverEvent
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/**
 * @author Enaium
 */
fun list(dispatcher: CommandDispatcher<ServerCommandSource>) {
    val COMPATIBILITY = CommandManager.literal("compatibility")
    dispatcher.register(
        ROOT.then(
            COMPATIBILITY.then(
                CommandManager.literal("list").executes { context: CommandContext<ServerCommandSource> ->
                    val compatibility = model.compatibility
                    var previous: Text? = null
                    for ((key, value) in compatibility) {
                        val enchantment = LiteralText(key).styled { style ->
                            style.setHoverEvent(
                                HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT,
                                    TranslatableText(
                                        Registry.ENCHANTMENT[Identifier(key)]?.translationKey ?: return@styled
                                    )
                                )
                            ).color = Formatting.AQUA
                        }
                        if (value.isNotEmpty()) {
                            enchantment.append(LiteralText(" -> ").styled { style -> style.color = Formatting.YELLOW })
                            for (s in value) {
                                enchantment.append(LiteralText(s).styled { style ->
                                    style.setHoverEvent(
                                        HoverEvent(
                                            HoverEvent.Action.SHOW_TEXT,
                                            TranslatableText(
                                                Registry.ENCHANTMENT[Identifier(key)]?.translationKey ?: return@styled
                                            )
                                        )
                                    ).color = Formatting.AQUA
                                })
                                if (s != value[value.size - 1]) {
                                    enchantment.append(LiteralText(", ").styled { style ->
                                        style.color = Formatting.YELLOW
                                    })
                                }
                            }
                        }
                        if (previous == null) {
                            previous = enchantment
                        } else {
                            previous.append(LiteralText(", ").styled { style -> style.color = Formatting.RED })
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
