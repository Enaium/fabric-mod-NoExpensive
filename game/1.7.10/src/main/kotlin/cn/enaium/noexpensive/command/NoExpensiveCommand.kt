package cn.enaium.noexpensive.command

import cn.enaium.noexpensive.Config.model
import cn.enaium.noexpensive.common.Action
import cn.enaium.noexpensive.event.ServerCommandCallbacks
import net.minecraft.command.AbstractCommand
import net.minecraft.command.CommandSource
import net.minecraft.command.NotFoundException
import net.minecraft.enchantment.Enchantment
import net.minecraft.text.*
import net.minecraft.util.Formatting
import kotlin.random.Random
import kotlin.random.nextInt

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

    override fun execute(commandSource: CommandSource, args: Array<String>) {
        if (args.isEmpty()) {
            commandSource.sendMessage(LiteralText("§cNoExpensive §7by §bEnaium"))
            return
        }
        if (args[0].equals("reload", ignoreCase = true)) {
            ServerCommandCallbacks.ReloadCallback.EVENT.invoker.execute()
            commandSource.sendMessage(TranslatableText("command.reload.success"))
        }

        if (args[0].equals("reset", ignoreCase = true)) {
            ServerCommandCallbacks.ResetCallback.EVENT.invoker.execute()
            commandSource.sendMessage(TranslatableText("command.reset.success"))
        }

        if (args[0].equals("maxLevel", ignoreCase = true)) {
            if (args.size == 1) {
                commandSource.sendMessage(TranslatableText("command.maxLevel.get", model.maxLevel))
            } else {
                ServerCommandCallbacks.MaxLevelCallback.EVENT.invoker.execute(args[1].toInt().let {
                    if (it < 0) {
                        commandSource.sendMessage(TranslatableText("command.maxLevel.error"))
                        return
                    } else {
                        it
                    }
                })
                commandSource.sendMessage(TranslatableText("command.maxLevel.success", args[1]))
            }
        }

        if (args[0].equals("combineHigher", ignoreCase = true)) {
            if (args.size == 1) {
                commandSource.sendMessage(TranslatableText("command.combineHigher.get", model.combineHigher))
            } else {
                ServerCommandCallbacks.CombineHigherCallback.EVENT.invoker.execute(args[1].toBoolean())
                commandSource.sendMessage(TranslatableText("command.combineHigher.success", args[1]))
            }
        }

        if (args[0].equals("compatibility", ignoreCase = true)) {
            if (args.size == 1) {
                throw NotFoundException()
            } else if (args.size == 2) {
                if (args[1].equals("list", ignoreCase = true)) {
                    val compatibility = model.compatibility
                    val text = LiteralText("")
                    for ((key, value) in compatibility) {
                        val enchantment = LiteralText(key).setStyle(
                            Style().setHoverEvent(
                                HoverEvent(
                                    HoverEventAction.SHOW_TEXT,
                                    TranslatableText(
                                        Enchantment.ALL_ENCHANTMENTS[key.toInt()]?.translationKey ?: continue
                                    )
                                )
                            ).setFormatting(Formatting.AQUA)
                        )
                        if (value.isNotEmpty()) {
                            enchantment.append(LiteralText(" -> ").setStyle(Style().setFormatting(Formatting.YELLOW)))
                            for (s in value) {
                                enchantment.append(
                                    LiteralText(s).setStyle(
                                        Style().setHoverEvent(
                                            HoverEvent(
                                                HoverEventAction.SHOW_TEXT,
                                                TranslatableText(
                                                    Enchantment.ALL_ENCHANTMENTS[key.toInt()]?.translationKey
                                                        ?: continue
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
                val compatibility = model.compatibility
                val enchantment1 = Enchantment.ALL_ENCHANTMENTS[args[2].toInt()]?.id?.toString() ?: let {
                    commandSource.sendMessage(TranslatableText("command.compatibility.notFound", args[2]))
                    return
                }
                val enchantment2 = Enchantment.ALL_ENCHANTMENTS[args[2].toInt()]?.id?.toString() ?: let {
                    commandSource.sendMessage(TranslatableText("command.compatibility.notFound", args[3]))
                    return
                }

                when (Action.valueOf(args[1])) {
                    Action.PUT -> {
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
                    }

                    Action.REMOVE -> {
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
                    }
                }
            }
        }
    }

    override fun method_3276(
        source: CommandSource,
        strings: Array<out String>
    ): List<String> {
        if (strings.size == 1) {
            return listOf("reload", "reset", "maxLevel", "compatibility", "combineHigher")
        }
        if (strings.size == 2) {
            return when (strings[0]) {
                "maxLevel" -> listOf(Random.nextInt(1..64).toString())
                "combineHigher" -> listOf("true", "false")
                else -> listOf("list") + Action.entries.map { it.name }
            }
        }
        if (strings.size == 3 || strings.size == 4) {
            if (strings[0].equals("compatibility", ignoreCase = true) && Action.entries.map { it.name }
                    .contains(strings[1])) {
                return Enchantment.ALL_ENCHANTMENTS.filterNotNull().map { it.id.toString() }
            }
        }
        return emptyList()
    }

    override fun getPermissionLevel(): Int {
        return 4
    }
}
