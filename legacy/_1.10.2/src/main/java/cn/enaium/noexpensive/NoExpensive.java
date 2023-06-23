package cn.enaium.noexpensive;

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback;
import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback;
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback;
import cn.enaium.noexpensive.callback.impl.AnvilSetOutputCallbackImpl;
import cn.enaium.noexpensive.callback.impl.AnvilTakeOutputCallbackImpl;
import cn.enaium.noexpensive.callback.impl.EnchantmentCanCombineCallbackImpl;
import cn.enaium.noexpensive.command.NoExpensiveCommand;
import net.fabricmc.api.ModInitializer;
import net.legacyfabric.fabric.api.registry.CommandRegistry;

/**
 * @author Enaium
 */
public class NoExpensive implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("Hello NoExpensive world!");

        CommandRegistry.INSTANCE.register(new NoExpensiveCommand());

        EnchantmentCanCombineCallback.EVENT.register(new EnchantmentCanCombineCallbackImpl());
        AnvilSetOutputCallback.EVENT.register(new AnvilSetOutputCallbackImpl());
        AnvilTakeOutputCallback.EVENT.register(new AnvilTakeOutputCallbackImpl());

        Config.load();
        Runtime.getRuntime().addShutdownHook(new Thread(Config::save));
    }
}
