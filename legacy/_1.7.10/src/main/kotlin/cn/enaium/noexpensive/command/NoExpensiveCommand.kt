package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.Config
import net.minecraft.command.AbstractCommand
import net.minecraft.command.CommandSource
import net.minecraft.command.NotFoundException
import net.minecraft.enchantment.Enchantment
import net.minecraft.text.*
import net.minecraft.util.Formatting

/**
 * @author Enaium
 */
class NoExpensiveCommand : AbstractCommand() {
    override fun compareTo(other: Any?): Int {
        return 0
    }

    override fun getCommandName(): String {
        return "noexpensive"
    }

    override fun getUsageTranslationKey(source: CommandSource): String? {
        return null
    }

    override fun execute(commandSource: CommandSource, args: Array<out String>) {
        if (args.isEmpty()) {
            commandSource.sendMessage(LiteralText("§cNoExpensive §7by §bEnaium"))
            return
        }
        if (args[0].equals("reload", ignoreCase = true)) {
            Config.load()
            commandSource.sendMessage(TranslatableText("command.reload.success"))
        }

        if (args[0].equals("maxLevel", ignoreCase = true)) {
            if (args.size == 1) {
                commandSource.sendMessage(TranslatableText("command.maxLevel.get", Config.model.maxLevel))
            } else {
                Config.model.maxLevel = args[1].toInt().let {
                    if (it < 0) {
                        commandSource.sendMessage(TranslatableText("command.maxLevel.error"))
                        return
                    } else {
                        it
                    }
                }
                commandSource.sendMessage(TranslatableText("command.maxLevel.success", args[1]))
                Config.save()
            }
        }

        if (args[0].equals("compatibility", ignoreCase = true)) {
            if (args.size == 1) {
                throw NotFoundException()
            } else if (args.size == 2) {
                if (args[1].equals("list", ignoreCase = true)) {
                    val compatibility = Config.model.compatibility
                    val text = LiteralText("")
                    for ((key, value) in compatibility) {
                        val enchantment = LiteralText(key.toString()).setStyle(
                            Style().setHoverEvent(
                                HoverEvent(
                                    HoverEventAction.SHOW_TEXT,
                                    TranslatableText(Enchantment.ALL_ENCHANTMENTS[key]?.translationKey ?: continue)
                                )
                            ).setFormatting(Formatting.AQUA)
                        )
                        if (value.isNotEmpty()) {
                            enchantment.append(LiteralText(" -> ").setStyle(Style().setFormatting(Formatting.YELLOW)))
                            for (s in value) {
                                enchantment.append(
                                    LiteralText(s.toString()).setStyle(
                                        Style().setHoverEvent(
                                            HoverEvent(
                                                HoverEventAction.SHOW_TEXT,
                                                TranslatableText(
                                                    Enchantment.ALL_ENCHANTMENTS[key]?.translationKey ?: continue
                                                )
                                            )
                                        ).setFormatting(Formatting.AQUA)
                                    )
                                )
                                if (s != value[value.size - 1]) {
                                    enchantment.append(LiteralText(", ").setStyle(Style().setFormatting(Formatting.YELLOW)))
                                }
                            }
                        }
                        text.append(enchantment)
                        if (key != compatibility.keys.last()) {
                            text.append(LiteralText(", ").setStyle(Style().setFormatting(Formatting.RED)))
                        }
                    }
                    commandSource.sendMessage(text)
                }
            } else if (args.size == 4) {
                val compatibility = Config.model.compatibility
                val enchantment1 = Enchantment.ALL_ENCHANTMENTS[args[2].toInt()]?.id ?: let {
                    commandSource.sendMessage(TranslatableText("command.compatibility.notFound", args[2]))
                    return
                }
                val enchantment2 = Enchantment.ALL_ENCHANTMENTS[args[2].toInt()]?.id ?: let {
                    commandSource.sendMessage(TranslatableText("command.compatibility.notFound", args[3]))
                    return
                }
                if (args[1].equals("put", ignoreCase = true)) {
                    if (compatibility.containsKey(enchantment1)) {
                        if (!compatibility[enchantment1]!!.contains(enchantment2)) {
                            compatibility[enchantment1]!!.add(enchantment2)
                            commandSource.sendMessage(
                                TranslatableText(
                                    "command.compatibility.put.success",
                                    enchantment1,
                                    enchantment2
                                )
                            )
                        }
                    } else {
                        compatibility[enchantment1] = ArrayList(listOf(enchantment2))
                        commandSource.sendMessage(
                            TranslatableText(
                                "command.compatibility.put.success",
                                enchantment1,
                                enchantment2
                            )
                        )
                    }
                } else if (args[1].equals("remove", ignoreCase = true)) {
                    if (compatibility.containsKey(enchantment1)) {
                        compatibility[enchantment1]!!.remove(enchantment2)
                        if (compatibility[enchantment1]!!.isEmpty()) {
                            compatibility.remove(enchantment1)
                        }
                        commandSource.sendMessage(
                            TranslatableText(
                                "command.compatibility.remove.success",
                                enchantment1,
                                enchantment2
                            )
                        )
                    }
                } else {
                    throw NotFoundException()
                }
            }
        }
    }

    override fun method_3276(
        source: CommandSource,
        strings: Array<out String>
    ): List<String> {
        if (strings.size == 1) {
            return listOf("reload", "maxLevel", "compatibility")
        }
        if (strings.size == 2) {
            if (strings[0].equals("compatibility", ignoreCase = true)) {
                return listOf("list", "put", "remove")
            }
        }
        if (strings.size == 3 || strings.size == 4) {
            if (strings[0].equals("compatibility", ignoreCase = true)) {
                return Enchantment.ALL_ENCHANTMENTS.filterNotNull().map { it.id.toString() }
            }
        }
        return emptyList()
    }

    override fun getPermissionLevel(): Int {
        return 4
    }
}