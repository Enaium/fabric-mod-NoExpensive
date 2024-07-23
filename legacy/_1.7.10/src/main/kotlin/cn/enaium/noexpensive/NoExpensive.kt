package cn.enaium.noexpensive

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback
import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback
import cn.enaium.noexpensive.callback.impl.*
import cn.enaium.noexpensive.command.NoExpensiveCommand
import net.legacyfabric.fabric.api.registry.CommandRegistry

/**
 * @author Enaium
 */
fun initializer() {
    println("Hello NoExpensive world!")
    CommandRegistry.INSTANCE.register(NoExpensiveCommand())
    EnchantmentCanCombineCallback.EVENT.register(EnchantmentCanCombineCallbackImpl())
    Config.load()
    Runtime.getRuntime().addShutdownHook(Thread { Config.save() })
}

object Client {
    @JvmStatic
    fun client() {
        AnvilSetOutputCallback.EVENT.register(AnvilSetOutputCallbackClientImpl())
        AnvilTakeOutputCallback.EVENT.register(AnvilTakeOutputCallbackClientImpl())
    }
}

object Server {
    @JvmStatic
    fun server() {
        AnvilSetOutputCallback.EVENT.register(AnvilSetOutputCallbackServerImpl())
        AnvilTakeOutputCallback.EVENT.register(AnvilTakeOutputCallbackServerImpl())
    }
}
