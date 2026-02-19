package cn.enaium.noexpensive

import cn.enaium.noexpensive.Config.model

/**
 * @author Enaium
 */
fun initializer() {
    if (model.compatibility.isEmpty()) {
        model.compatibility = mutableMapOf(
            "16" to mutableListOf("17", "18"),
            "17" to mutableListOf("18", "16"),
            "18" to mutableListOf("16", "17"),
            "0" to mutableListOf("5", "3", "1"),
            "5" to mutableListOf("3", "1", "0"),
            "3" to mutableListOf("1", "0", "5"),
            "1" to mutableListOf("0", "5", "3")
        )
    }
}
