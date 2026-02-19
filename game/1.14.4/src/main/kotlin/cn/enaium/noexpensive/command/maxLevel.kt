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
import cn.enaium.noexpensive.event.ServerCommandCallbacks
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.TranslatableText

/**
 * @author Enaium
 */
fun maxLevelCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
    dispatcher.register(
        ROOT.then(
            CommandManager.literal("maxLevel").executes { context: CommandContext<ServerCommandSource> ->
                context.source.sendFeedback(
                    TranslatableText("command.maxLevel.get", model.maxLevel), false
                )
                Command.SINGLE_SUCCESS
            }.then(
                CommandManager.argument("level", IntegerArgumentType.integer())
                    .executes { context: CommandContext<ServerCommandSource> ->
                        ServerCommandCallbacks.MaxLevelCallback.EVENT.invoker.execute(
                            IntegerArgumentType.getInteger(
                                context,
                                "level"
                            )
                        )
                        context.source.sendFeedback(
                            TranslatableText(
                                "command.maxLevel.success",
                                IntegerArgumentType.getInteger(context, "level")
                            ), false
                        )
                        Command.SINGLE_SUCCESS
                    })
        )
    )
}