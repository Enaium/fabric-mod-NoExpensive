/*
 * Copyright (c) 2026 Enaium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.enaium.noexpensive

import cn.enaium.noexpensive.Config.model
import net.minecraft.commands.Commands

/**
 * @author Enaium
 */
fun initializer() {
    if (model.compatibility.isEmpty()) {
        model.compatibility = mutableMapOf(
            "minecraft:mending" to mutableListOf("minecraft:infinity"),
            "minecraft:multishot" to mutableListOf("minecraft:piercing"),
            "minecraft:sharpness" to mutableListOf("minecraft:smite", "minecraft:bane_of_arthropods"),
            "minecraft:smite" to mutableListOf("minecraft:bane_of_arthropods", "minecraft:sharpness"),
            "minecraft:bane_of_arthropods" to mutableListOf("minecraft:sharpness", "minecraft:smite"),
            "minecraft:density" to mutableListOf(
                "minecraft:breach",
                "minecraft:sharpness",
                "minecraft:smite",
                "minecraft:bane_of_arthropods"
            ),
            "minecraft:breach" to mutableListOf(
                "minecraft:density",
                "minecraft:sharpness",
                "minecraft:smite",
                "minecraft:bane_of_arthropods"
            ),
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

val ROOT = Commands.literal("noexpensive")
    .requires(Commands.hasPermission(Commands.LEVEL_OWNERS))
