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
            16 to mutableListOf(17, 18),
            17 to mutableListOf(18, 16),
            18 to mutableListOf(16, 17),
            0 to mutableListOf(5, 3, 1),
            5 to mutableListOf(3, 1, 0),
            3 to mutableListOf(1, 0, 5),
            1 to mutableListOf(0, 5, 3)
        )
    }
}
