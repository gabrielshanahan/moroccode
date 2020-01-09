/*
 * MIT License
 *
 * Copyright (c) 2019 gabrielshanahan
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

package io.github.gabrielshanahan.moroccode

internal class DummyCompareBySingleField(private val f1: String? = "Hello", private val f2: Number? = 5) {
    override fun hashCode() = hash(f1, f2)
    override fun equals(other: Any?): Boolean = compareByFields(other) { fields { f1 } }
}

internal class DummyCompareByFields(private val f1: String? = "Hello", private val f2: Number? = 5) {
    override fun hashCode() = hash(f1, f2)
    override fun equals(other: Any?): Boolean = compareByFields(other) { fields { f1 } and { f2 } }
}

internal class DummyCompareBy(private val f1: String? = "Hello", private val f2: Number? = 5) {
    override fun hashCode() = hash(f1, f2)
    override fun equals(other: Any?): Boolean = compareUsing(other) { f1 == it.f1 && f2 == it.f2 }
}

internal class Dummy(val f1: String? = "Hello", val f2: Number? = 5)
