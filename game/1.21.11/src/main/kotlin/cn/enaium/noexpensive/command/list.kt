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
import net.minecraft.text.MutableText
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

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
                    var previous: MutableText? = null
                    for ((key, value) in compatibility) {
                        val enchantment = Text.literal(key).styled { style: Style ->
                            style.withHoverEvent(
                                HoverEvent.ShowText(
                                    Text.translatable(Identifier.of(key).toTranslationKey("enchantment"))
                                )
                            ).withColor(
                                Formatting.AQUA
                            )
                        }
                        if (value.isNotEmpty()) {
                            enchantment.append(
                                Text.literal(" -> ").styled { style: Style -> style.withColor(Formatting.YELLOW) })
                            for (s in value) {
                                enchantment.append(Text.literal(s).styled { style: Style ->
                                    style.withHoverEvent(
                                        HoverEvent.ShowText(
                                            Text.translatable(Identifier.of(s).toTranslationKey("enchantment"))
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
                        context.source.sendFeedback({ previous }, false)
                    }
                    Command.SINGLE_SUCCESS
                })
        )
    )
}
