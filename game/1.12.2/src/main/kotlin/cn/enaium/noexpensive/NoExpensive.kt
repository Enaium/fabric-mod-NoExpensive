package cn.enaium.noexpensive

import cn.enaium.noexpensive.config.NoExpensiveConfig

/**
 * @author Enaium
 */
fun initializer() {
    if (NoExpensiveConfig.compatibility.value.isEmpty()) {
        NoExpensiveConfig.compatibility = NoExpensiveConfig.compatibility.copy(
            defaultValue = mutableMapOf(
                "minecraft:mending" to mutableListOf("minecraft:infinity"),
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
        )
    }
}
