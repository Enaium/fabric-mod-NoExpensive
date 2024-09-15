package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.Config
import cn.enaium.noexpensive.ROOT
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.client.MinecraftClient
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

/**
 * @author Enaium
 */
fun reset(dispatcher: CommandDispatcher<ServerCommandSource>) {
    dispatcher.register(
        ROOT.then(
            CommandManager.literal("reset").executes { context: CommandContext<ServerCommandSource> ->
                MinecraftClient.getInstance().execute {
                    Config.reset()
                    context.source.sendFeedback(Text.translatable("command.reset.success"), false)
                }
                Command.SINGLE_SUCCESS
            })
    )
}