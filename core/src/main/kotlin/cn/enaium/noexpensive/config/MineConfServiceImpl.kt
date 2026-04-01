package cn.enaium.noexpensive.config

import cn.enaium.mineconf.core.MineConf
import cn.enaium.mineconf.core.MineConfService

/**
 * @author Enaium
 */
class MineConfServiceImpl : MineConfService {
    override fun conf(): MineConf {
        return MineConf("noexpensive", "NoExpensive", NoExpensiveConfig)
    }
}