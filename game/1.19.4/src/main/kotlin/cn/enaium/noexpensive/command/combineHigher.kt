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
import com.mojang.brigadier.arguments.BoolArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

/**
 * @author Enaium
 */
fun combineHigherCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
    dispatcher.register(ROOT.then(CommandManager.literal("combineHigher").executes {
        it.source.sendFeedback(Text.translatable("command.combineHigher.get", model.combineHigher), false)
        Command.SINGLE_SUCCESS
    }.then(CommandManager.argument("combineHigher", BoolArgumentType.bool()).executes {
        ServerCommandCallbacks.CombineHigherCallback.EVENT.invoker.execute(
            BoolArgumentType.getBool(
                it,
                "combineHigher"
            )
        )
        it.source.sendFeedback(Text.translatable("command.combineHigher.success", model.combineHigher), false)
        Command.SINGLE_SUCCESS
    })))
}