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
    override fun compareTo(other: Any?): Int = 0

    override fun getCommandName(): String {
        return "noexpensive"
    }

    override fun getUsageTranslationKey(source: CommandSource): String? {
        return null
    }

    override fun execute(source: CommandSource, args: Array<out String>) {
        if (args.isEmpty()) {
            source.sendMessage(LiteralText("§cNoExpensive §7by §bEnaium"))
            return
        }
        if (args[0].equals("reload", ignoreCase = true)) {
            Config.load()
            source.sendMessage(TranslatableText("command.reload.success"))
        }
    }
}
