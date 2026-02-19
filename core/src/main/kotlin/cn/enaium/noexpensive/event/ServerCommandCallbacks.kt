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

package cn.enaium.noexpensive.event

import cn.enaium.noexpensive.common.Action

/**
 * @author Enaium
 */
class ServerCommandCallbacks {
    fun interface CombineHigherCallback {
        companion object {
            val EVENT = Event { listeners: List<CombineHigherCallback> ->
                CombineHigherCallback { combineHigher ->
                    for (listener in listeners) {
                        listener.execute(combineHigher)
                    }
                }
            }
        }

        fun execute(combineHigher: Boolean)
    }

    fun interface ActionCallback {
        companion object {
            val EVENT = Event { listeners: List<ActionCallback> ->
                ActionCallback { action, enchantment, other ->
                    var result = false
                    for (listener in listeners) {
                        result = listener.execute(action, enchantment, other)
                    }
                    return@ActionCallback result
                }
            }
        }

        fun execute(action: Action, enchantment: String, other: String): Boolean
    }

    fun interface MaxLevelCallback {
        companion object {
            val EVENT = Event { listeners: List<MaxLevelCallback> ->
                MaxLevelCallback { level ->
                    for (listener in listeners) {
                        listener.execute(level)
                    }
                }
            }
        }

        fun execute(level: Int)
    }

    fun interface ReloadCallback {
        companion object {
            val EVENT = Event { listeners: List<ReloadCallback> ->
                ReloadCallback {
                    for (listener in listeners) {
                        listener.execute()
                    }
                }
            }
        }

        fun execute()
    }

    fun interface ResetCallback {
        companion object {
            val EVENT = Event { listeners: List<ResetCallback> ->
                ResetCallback {
                    for (listener in listeners) {
                        listener.execute()
                    }
                }
            }
        }

        fun execute()
    }
}