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

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * @author Enaium
 */
class Event<T>(
    private val invokerFactory: (List<T>) -> T
) {
    private val lock = ReentrantLock()
    private val handlers = mutableListOf<T>()
    var invoker: T = invokerFactory(emptyList())
        private set

    private fun update() {
        invoker = when {
            handlers.isEmpty() -> invokerFactory(emptyList())
            handlers.size == 1 -> handlers[0]
            else -> invokerFactory(handlers)
        }
    }

    fun register(listener: T) {
        lock.withLock {
            handlers.add(listener)
            update()
        }
    }
}