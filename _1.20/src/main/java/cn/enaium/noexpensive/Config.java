package cn.enaium.noexpensive;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Enaium
 */
public class Config {

    private static final File configFile = new File(MinecraftClient.getInstance().runDirectory, "NoExpensive.json");
    private static Model model = new Model();

    public static Model getModel() {
        return model;
    }

    public static class Model {
        public int maxLevel = 39;
        public Map<String, List<String>> compatibility = new HashMap<>();
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
}
