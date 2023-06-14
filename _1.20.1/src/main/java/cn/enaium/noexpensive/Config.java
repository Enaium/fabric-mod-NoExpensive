package cn.enaium.noexpensive;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Enaium
 */
public class Config {

    private static final File configFile = new File(System.getProperty("user.dir"), "NoExpensive.json");
    private static Model model = new Model();

    public static Model getModel() {
        return model;
    }

    public static void load() {
        if (configFile.exists()) {
            try {
                model = new Gson().fromJson(FileUtils.readFileToString(configFile, StandardCharsets.UTF_8), Model.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try {
            FileUtils.write(configFile, new GsonBuilder().setPrettyPrinting().create().toJson(model), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Model {
        public int maxLevel = 39;

        public Map<String, List<String>> compatibility = new HashMap<>(
                Map.of(
                        "minecraft:mending", new ArrayList<>(List.of("minecraft:infinity")),
                        "minecraft:multishot", new ArrayList<>(List.of("minecraft:piercing")),
                        "minecraft:sharpness", new ArrayList<>(List.of("minecraft:smite", "minecraft:bane_of_arthropods")),
                        "minecraft:smite", new ArrayList<>(List.of("minecraft:bane_of_arthropods", "minecraft:sharpness")),
                        "minecraft:bane_of_arthropods", new ArrayList<>(List.of("minecraft:sharpness", "minecraft:smite")),
                        "minecraft:protection", new ArrayList<>(List.of("minecraft:projectile_protection", "minecraft:blast_protection", "minecraft:fire_protection")),
                        "minecraft:projectile_protection", new ArrayList<>(List.of("minecraft:blast_protection", "minecraft:fire_protection", "minecraft:protection")),
                        "minecraft:blast_protection", new ArrayList<>(List.of("minecraft:fire_protection", "minecraft:protection", "minecraft:projectile_protection")),
                        "minecraft:fire_protection", new ArrayList<>(List.of("minecraft:protection", "minecraft:projectile_protection", "minecraft:blast_protection"))
                )
        );
    }
}
