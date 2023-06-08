package cn.enaium.noexpensive.command;

import cn.enaium.noexpensive.Config;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static cn.enaium.noexpensive.NoExpensive.ROOT;

/**
 * @author Enaium
 */
public class MaxLevelCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(ROOT.then(CommandManager.literal("maxLevel").executes(context -> {
            context.getSource().sendFeedback(() -> Text.translatable("command.maxLevel.get", Config.getModel().maxLevel), false);
            return Command.SINGLE_SUCCESS;
        }).then(CommandManager.argument("level", IntegerArgumentType.integer()).executes(context -> {
            Config.getModel().maxLevel = IntegerArgumentType.getInteger(context, "level");
            context.getSource().sendFeedback(() -> Text.translatable("command.maxLevel.success", IntegerArgumentType.getInteger(context, "level")), false);
            Config.save();
            return Command.SINGLE_SUCCESS;
        }))));
    }
}
