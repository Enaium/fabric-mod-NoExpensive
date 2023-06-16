package cn.enaium.noexpensive;

import cn.enaium.noexpensive.callback.AnvilSetOutputCallback;
import cn.enaium.noexpensive.callback.AnvilTakeOutputCallback;
import cn.enaium.noexpensive.callback.EnchantmentCanCombineCallback;
import cn.enaium.noexpensive.callback.impl.AnvilSetOutputCallbackImpl;
import cn.enaium.noexpensive.callback.impl.AnvilTakeOutputCallbackImpl;
import cn.enaium.noexpensive.callback.impl.EnchantmentCanCombineCallbackImpl;
import cn.enaium.noexpensive.command.CompatibilityCommand;
import cn.enaium.noexpensive.command.MaxLevelCommand;
import cn.enaium.noexpensive.command.ReloadCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Enaium
 */
public class NoExpensive implements ModInitializer {

    public static final LiteralArgumentBuilder<ServerCommandSource> ROOT = literal("noexpensive").requires(source -> source.hasPermissionLevel(4));

    @Override
    public void onInitialize() {
        System.out.println("Hello NoExpensive world!");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            MaxLevelCommand.register(dispatcher);
            CompatibilityCommand.register(dispatcher, registryAccess);
            ReloadCommand.register(dispatcher);
        });

        EnchantmentCanCombineCallback.EVENT.register(new EnchantmentCanCombineCallbackImpl());
        AnvilSetOutputCallback.EVENT.register(new AnvilSetOutputCallbackImpl());
        AnvilTakeOutputCallback.EVENT.register(new AnvilTakeOutputCallbackImpl());

        Config.load();
        Runtime.getRuntime().addShutdownHook(new Thread(Config::save));
    }
}
