package cn.enaium.noexpensive.command;

import cn.enaium.noexpensive.Config;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static cn.enaium.noexpensive.NoExpensive.ROOT;

/**
 * @author Enaium
 */
public class ReloadCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(ROOT.then(CommandManager.literal("reload").executes(context -> {
            Config.load();
            context.getSource().sendFeedback(() -> Text.translatable("command.reload.success"), false);
            return Command.SINGLE_SUCCESS;
        })));
    }
}
