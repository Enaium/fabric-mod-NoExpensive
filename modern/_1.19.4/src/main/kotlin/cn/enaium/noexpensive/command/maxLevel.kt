package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.Config
import cn.enaium.noexpensive.ROOT
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

/**
 * @author Enaium
 */
fun maxLevelCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
    dispatcher.register(
        ROOT.then(
            CommandManager.literal("maxLevel").executes { context: CommandContext<ServerCommandSource> ->
                context.source.sendFeedback(
                    Text.translatable("command.maxLevel.get", Config.model.maxLevel), false
                )
                Command.SINGLE_SUCCESS
            }.then(
                CommandManager.argument("level", IntegerArgumentType.integer())
                    .executes { context: CommandContext<ServerCommandSource> ->
                        Config.model.maxLevel = IntegerArgumentType.getInteger(context, "level")
                        context.source.sendFeedback(
                            Text.translatable(
                                "command.maxLevel.success",
                                IntegerArgumentType.getInteger(context, "level")
                            ), false
                        )
                        Config.save()
                        Command.SINGLE_SUCCESS
                    })
        )
    )
}