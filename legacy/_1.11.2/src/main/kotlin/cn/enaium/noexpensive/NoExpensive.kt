package cn.enaium.noexpensive

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback
import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback
import cn.enaium.noexpensive.callback.impl.AnvilSetOutputCallbackImpl
import cn.enaium.noexpensive.callback.impl.AnvilTakeOutputCallbackImpl
import cn.enaium.noexpensive.callback.impl.EnchantmentCanCombineCallbackImpl
import cn.enaium.noexpensive.command.NoExpensiveCommand
import net.legacyfabric.fabric.api.registry.CommandRegistry

/**
 * @author Enaium
 */
fun initializer() {
    println("Hello NoExpensive world!")
    CommandRegistry.INSTANCE.register(NoExpensiveCommand())
    EnchantmentCanCombineCallback.EVENT.register(EnchantmentCanCombineCallbackImpl())
    AnvilSetOutputCallback.EVENT.register(AnvilSetOutputCallbackImpl())
    AnvilTakeOutputCallback.EVENT.register(AnvilTakeOutputCallbackImpl())
    Config.load()
    Runtime.getRuntime().addShutdownHook(Thread { Config.save() })
}
