package cn.enaium.noexpensive

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.IOException

/**
 * @author Enaium
 */
object Config {
    private val configFile = File(System.getProperty("user.dir"), "NoExpensive.json")
    var model = Model()
        private set

    fun load() {
        if (configFile.exists()) {
            try {
                model =
                    Gson().fromJson(configFile.readText(Charsets.UTF_8), Model::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            save()
        }
    }

    fun save() {
        try {
            configFile.writeText(GsonBuilder().setPrettyPrinting().create().toJson(model), Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    class Model {
        var maxLevel = 39
        var compatibility = mutableMapOf(
            "minecraft:mending" to mutableListOf("minecraft:infinity"),
            "minecraft:multishot" to mutableListOf("minecraft:piercing"),
            "minecraft:sharpness" to mutableListOf("minecraft:smite", "minecraft:bane_of_arthropods"),
            "minecraft:smite" to mutableListOf("minecraft:bane_of_arthropods", "minecraft:sharpness"),
            "minecraft:bane_of_arthropods" to mutableListOf("minecraft:sharpness", "minecraft:smite"),
            "minecraft:protection" to mutableListOf(
                "minecraft:projectile_protection",
                "minecraft:blast_protection",
                "minecraft:fire_protection"
            ),
            "minecraft:projectile_protection" to mutableListOf(
                "minecraft:blast_protection",
                "minecraft:fire_protection",
                "minecraft:protection"
            ),
            "minecraft:blast_protection" to mutableListOf(
                "minecraft:fire_protection",
                "minecraft:protection",
                "minecraft:projectile_protection"
            ),
            "minecraft:fire_protection" to mutableListOf(
                "minecraft:protection",
                "minecraft:projectile_protection",
                "minecraft:blast_protection"
            )
        )
    }
}
