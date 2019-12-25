/*
 * MIT License
 *
 * Copyright (c) 2017 Pim van den Berg
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

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

internal class DifferenceTest : StringSpec({
    val same1 = Dummy()
    val same2 = Dummy()

    val different1 = Dummy("Hello")
    val different2 = Dummy("World")

    "Difference by fields behaves correctly" {

        val diff = same1.diffByFields(same2) { listOf(f1, f2) }
        val diff2 = different1.diffByFields(different2) { listOf(f1, f2) }

        diff shouldBe emptyList()
        diff2.size shouldBe 1
        diff2.single() shouldBe FieldDifference(different1 to "Hello", different2 to "World")
    }

    "Custom difference behaves correctly" {

        val diffSame = different1.diffBy(different2) {
            if (f1?.length != it.f1?.length) listOf(f1 to it.f1) else emptyList()
        }

        val diffDiff = different1.diffBy(different2) {
            if (f1?.firstOrNull() != it.f1?.firstOrNull()) listOf(f1 to it.f1) else emptyList()
        }

        diffSame shouldBe emptyList()
        diffDiff.size shouldBe 1
        diffDiff.single() shouldBe FieldDifference(different1 to "Hello", different2 to "World")
    }
})
