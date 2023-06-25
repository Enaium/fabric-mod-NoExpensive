package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.Config
import net.minecraft.command.AbstractCommand
import net.minecraft.command.CommandException
import net.minecraft.command.CommandSource
import net.minecraft.server.MinecraftServer
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText

/**
 * @author Enaium
 */
class NoExpensiveCommand : AbstractCommand() {
    override fun getCommandName(): String {
        return "noexpensive"
    }

    override fun getUsageTranslationKey(source: CommandSource): String? {
        return null
    }

    override fun method_3279(minecraftServer: MinecraftServer, commandSource: CommandSource, args: Array<String>) {
        if (args.isEmpty()) {
            commandSource.sendMessage(LiteralText("§cNoExpensive §7by §bEnaium"))
            return
        }
        if (args[0].equals("reload", ignoreCase = true)) {
            Config.load()
            commandSource.sendMessage(TranslatableText("command.reload.success"))
        }
    }
}
