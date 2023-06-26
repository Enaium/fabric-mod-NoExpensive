package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.Config
import cn.enaium.noexpensive.ROOT
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.TranslatableText

/**
 * @author Enaium
 */
fun reloadCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
    dispatcher.register(
        ROOT.then(
            CommandManager.literal("reload").executes { context: CommandContext<ServerCommandSource> ->
                Config.load()
                context.source.sendFeedback(TranslatableText("command.reload.success"), false)
                Command.SINGLE_SUCCESS
            })
    )
}
