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

        public Map<Integer, List<Integer>> compatibility =
                new HashMap<>(
                        ImmutableMap.<Integer, List<Integer>>builder()
                                .put(16, new ArrayList<>(ImmutableList.<Integer>builder().add(17).add(18).build()))
                                .put(17, new ArrayList<>(ImmutableList.<Integer>builder().add(18).add(16).build()))
                                .put(18, new ArrayList<>(ImmutableList.<Integer>builder().add(16).add(17).build()))
                                .put(0, new ArrayList<>(ImmutableList.<Integer>builder().add(5).add(3).add(1).build()))
                                .put(5, new ArrayList<>(ImmutableList.<Integer>builder().add(3).add(1).add(0).build()))
                                .put(3, new ArrayList<>(ImmutableList.<Integer>builder().add(1).add(0).add(5).build()))
                                .put(1, new ArrayList<>(ImmutableList.<Integer>builder().add(0).add(5).add(3).build()))
                                .build()
                );
    }
}
