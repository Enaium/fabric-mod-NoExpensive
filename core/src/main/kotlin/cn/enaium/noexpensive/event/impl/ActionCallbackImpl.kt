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

package cn.enaium.noexpensive.event.impl

import cn.enaium.noexpensive.Config.model
import cn.enaium.noexpensive.common.Action
import cn.enaium.noexpensive.event.ServerCommandCallbacks

class ActionCallbackImpl : ServerCommandCallbacks.ActionCallback {
    override fun execute(
        action: Action,
        enchantment: String,
        other: String
    ): Boolean {
        val compatibility = model.compatibility
        if (action === Action.PUT) {
            if (compatibility.containsKey(enchantment)) {
                if (!compatibility[enchantment]!!.contains(other)) {
                    compatibility[enchantment]!!.add(other)
                    return true
                }
            } else {
                compatibility[enchantment] =
                    ArrayList(listOf(other))
                return true
            }
        } else if (action === Action.REMOVE) {
            if (compatibility.containsKey(enchantment)) {
                compatibility[enchantment]!!.remove(other)
                if (compatibility[enchantment]!!.isEmpty()) {
                    compatibility.remove(enchantment)
                }
                return true
            }
        }
        return false
    }
}