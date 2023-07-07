package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.Config.model
import cn.enaium.noexpensive.ROOT
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.TranslatableText

/**
 * @author Enaium
 */
fun combineHigherCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
    dispatcher.register(ROOT.then(CommandManager.literal("combineHigher").executes {
        it.source.sendFeedback(TranslatableText("command.combineHigher.get", model.combineHigher), false)
        Command.SINGLE_SUCCESS
    }.then(CommandManager.argument("combineHigher", BoolArgumentType.bool()).executes {
        model.combineHigher = BoolArgumentType.getBool(it, "combineHigher")
        it.source.sendFeedback(TranslatableText("command.combineHigher.success", model.combineHigher), false)
        Command.SINGLE_SUCCESS
    })))
}