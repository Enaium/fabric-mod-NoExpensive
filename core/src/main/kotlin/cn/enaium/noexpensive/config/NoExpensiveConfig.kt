package cn.enaium.noexpensive.config

import cn.enaium.mineconf.core.ConfBuilder

/**
 * @author Enaium
 */
object NoExpensiveConfig {
    var maxLevel = ConfBuilder.create()
        .id("max_leve").name("Max Level")
        .description("Maximum repair cost level")
        .literal<Int>().build(39)

    var combineHigher = ConfBuilder.create()
        .id("combine_higher").name("Combine Higher")
        .description("Combining higher-level enchantments")
        .literal<Boolean>().build(false)

    var compatibility = ConfBuilder.create()
        .id("compatibility").name("Compatibility")
        .description("Compatibility")
        .multimap<String, String>().build(mutableMapOf())
}