package cn.enaium.noexpensive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

        public Map<String, List<String>> compatibility =
                new HashMap<>(
                        ImmutableMap.<String, List<String>>builder()
                                .put("minecraft:sharpness", new ArrayList<>(ImmutableList.<String>builder().add("minecraft:smite").add("minecraft:bane_of_arthropods").build()))
                                .put("minecraft:smite", new ArrayList<>(ImmutableList.<String>builder().add("minecraft:bane_of_arthropods").add("minecraft:sharpness").build()))
                                .put("minecraft:bane_of_arthropods", new ArrayList<>(ImmutableList.<String>builder().add("minecraft:sharpness").add("minecraft:smite").build()))
                                .put("minecraft:protection", new ArrayList<>(ImmutableList.<String>builder().add("minecraft:projectile_protection").add("minecraft:blast_protection").add("minecraft:fire_protection").build()))
                                .put("minecraft:projectile_protection", new ArrayList<>(ImmutableList.<String>builder().add("minecraft:blast_protection").add("minecraft:fire_protection").add("minecraft:protection").build()))
                                .put("minecraft:blast_protection", new ArrayList<>(ImmutableList.<String>builder().add("minecraft:fire_protection").add("minecraft:protection").add("minecraft:projectile_protection").build()))
                                .put("minecraft:fire_protection", new ArrayList<>(ImmutableList.<String>builder().add("minecraft:protection").add("minecraft:projectile_protection").add("minecraft:blast_protection").build()))
                                .build()
                );
    }
}
