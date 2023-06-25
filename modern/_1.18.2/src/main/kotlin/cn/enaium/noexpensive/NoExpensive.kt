package cn.enaium.noexpensive

import callback.AnvilSetOutputCallback
import callback.AnvilTakeOutputCallback
import callback.EnchantmentCanCombineCallback
import callback.impl.AnvilSetOutputCallbackImpl
import callback.impl.AnvilTakeOutputCallbackImpl
import callback.impl.EnchantmentCanCombineCallbackImpl
import command.compatibilityCommand
import command.maxLevelCommand
import command.reloadCommand
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource

/**
 * @author Enaium
 */
fun initializer() {
    println("Hello NoExpensive world!")
    CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, _: Boolean ->
        maxLevelCommand(dispatcher)
        compatibilityCommand(dispatcher)
        reloadCommand(dispatcher)
    })
    EnchantmentCanCombineCallback.EVENT.register(
        EnchantmentCanCombineCallbackImpl()
    )
    AnvilSetOutputCallback.EVENT.register(AnvilSetOutputCallbackImpl())
    AnvilTakeOutputCallback.EVENT.register(AnvilTakeOutputCallbackImpl())
    Config.load()
    Runtime.getRuntime().addShutdownHook(Thread { Config.save() })
}

val ROOT = CommandManager.literal("noexpensive")
    .requires { source: ServerCommandSource -> source.hasPermissionLevel(4) }
