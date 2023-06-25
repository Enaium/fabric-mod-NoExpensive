package cn.enaium.noexpensive

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback
import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback
import cn.enaium.noexpensive.callback.impl.AnvilSetOutputCallbackImpl
import cn.enaium.noexpensive.callback.impl.AnvilTakeOutputCallbackImpl
import cn.enaium.noexpensive.callback.impl.EnchantmentCanCombineCallbackImpl
import cn.enaium.noexpensive.command.compatibilityCommand
import cn.enaium.noexpensive.command.maxLevelCommand
import cn.enaium.noexpensive.command.reloadCommand
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource

/**
 * @author Enaium
 */
fun initializer() {
    println("Hello NoExpensive world!")
    CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess, environment: CommandManager.RegistrationEnvironment ->
        maxLevelCommand(dispatcher)
        compatibilityCommand(dispatcher, registryAccess)
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
