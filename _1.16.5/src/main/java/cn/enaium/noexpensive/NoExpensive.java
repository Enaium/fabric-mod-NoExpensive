package cn.enaium.noexpensive;

import cn.enaium.noexpensive.command.CompatibilityCommand;
import cn.enaium.noexpensive.command.MaxLevelCommand;
import cn.enaium.noexpensive.command.ReloadCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Enaium
 */
public class NoExpensive implements ModInitializer {

    public static final LiteralArgumentBuilder<ServerCommandSource> ROOT = literal("noexpensive").requires(source -> source.hasPermissionLevel(4));

    public static boolean canCombine0(Enchantment enchantment1, Enchantment enchantment2) {
        final String enchantment1Name = Objects.requireNonNull(Registry.ENCHANTMENT.getId(enchantment1)).toString();
        final String enchantment2Name = Objects.requireNonNull(Registry.ENCHANTMENT.getId(enchantment2)).toString();

        final Map<String, List<String>> compatibility = Config.getModel().compatibility;

        if (compatibility.containsKey(enchantment1Name) && compatibility.get(enchantment1Name).contains(enchantment2Name)) {
            return true;
        } else if (compatibility.containsKey(enchantment2Name) && compatibility.get(enchantment2Name).contains(enchantment1Name)) {
            return true;
        }
        return enchantment1.canCombine(enchantment2);
    }

    @Override
    public void onInitialize() {
        System.out.println("Hello NoExpensive world!");

        CommandRegistrationCallback.EVENT.register((dispatcher, environment) -> {
            MaxLevelCommand.register(dispatcher);
            CompatibilityCommand.register(dispatcher);
            ReloadCommand.register(dispatcher);
        });

        Config.load();
        Runtime.getRuntime().addShutdownHook(new Thread(Config::save));
    }
}
