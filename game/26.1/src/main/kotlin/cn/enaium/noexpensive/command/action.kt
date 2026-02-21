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
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.ResourceArgument
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.Style
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items

/**
 * @author Enaium
 */
fun action(dispatcher: CommandDispatcher<CommandSourceStack>, buildContext: CommandBuildContext) {
    val COMPATIBILITY = Commands.literal("compatibility")
    for (action in Action.entries) {
        dispatcher.register(
            ROOT.then(
                COMPATIBILITY.then(
                    Commands.literal(action.name)
                        .then(
                            Commands.argument(
                                "enchantment1",
                                ResourceArgument.resource(buildContext, Registries.ENCHANTMENT)
                            )
                                .then(
                                    Commands.argument(
                                        "enchantment2",
                                        ResourceArgument.resource(buildContext, Registries.ENCHANTMENT)
                                    ).executes { context: CommandContext<CommandSourceStack> ->
                                        val enchantment = ResourceArgument.getEnchantment(context, "enchantment1")
                                        val other = ResourceArgument.getEnchantment(context, "enchantment2")
                                        val enchantmentName = enchantment.registeredName
                                        val otherName = other.registeredName
                                        val enchantmentItemStack = Items.ENCHANTED_BOOK.defaultInstance
                                        val otherItemStack = Items.ENCHANTED_BOOK.defaultInstance
                                        enchantmentItemStack.enchant(enchantment, 1)
                                        otherItemStack.enchant(other, 1)
                                        val enchantmentText =
                                            Component.literal(enchantmentName).withStyle { style: Style ->
                                                style.withHoverEvent(
                                                    HoverEvent.ShowItem(
                                                        ItemStackTemplate.fromNonEmptyStack(enchantmentItemStack)
                                                    )
                                                ).withColor(ChatFormatting.AQUA)
                                            }
                                        val otherText =
                                            Component.literal(otherName).withStyle { style: Style ->
                                                style.withHoverEvent(
                                                    HoverEvent.ShowItem(
                                                        ItemStackTemplate.fromNonEmptyStack(otherItemStack)
                                                    )
                                                ).withColor(ChatFormatting.AQUA)
                                            }
                                        if (ServerCommandCallbacks.ActionCallback.EVENT.invoker.execute(
                                                action,
                                                otherName,
                                                otherName
                                            )
                                        ) {
                                            context.source.sendSystemMessage(
                                                Component.translatable(
                                                    when (action) {
                                                        Action.PUT -> "command.compatibility.put.success"
                                                        Action.REMOVE -> "command.compatibility.remove.success"
                                                    },
                                                    enchantmentText,
                                                    otherText
                                                )
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
