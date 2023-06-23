package cn.enaium.noexpensive.command;

import cn.enaium.noexpensive.Config;
import net.minecraft.command.AbstractCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

/**
 * @author Enaium
 */
public class NoExpensiveCommand extends AbstractCommand {

    @Override
    public String getCommandName() {
        return "noexpensive";
    }

    @Override
    public String getUsageTranslationKey(CommandSource source) {
        return null;
    }

    @Override
    public void method_3279(MinecraftServer minecraftServer, CommandSource commandSource, String[] args) throws CommandException {
        if (args.length == 0) {
            commandSource.sendMessage(new LiteralText("§cNoExpensive §7by §bEnaium"));
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            Config.load();
            commandSource.sendMessage(new TranslatableText("command.reload.success"));
        }
    }
}
