package cn.enaium.noexpensive.command;

import cn.enaium.noexpensive.Config;
import net.minecraft.command.AbstractCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;

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
    public void execute(CommandSource source, String[] args) {
        if (args.length == 0) {
            source.sendMessage(new LiteralText("§cNoExpensive §7by §bEnaium"));
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            Config.load();
            source.sendMessage(new TranslatableText("command.reload.success"));
        }
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }
}
