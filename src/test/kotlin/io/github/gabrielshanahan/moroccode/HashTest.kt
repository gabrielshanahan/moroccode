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

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

internal class HashTest : StringSpec({
    "Hash is unique" {
        hash("moroccode.Test", 1, 2, 3) shouldNotBe hash(1, 2, 3, "moroccode.Test")
        hash(Any()) shouldNotBe hash(Any())
    }

    "Hash is consistent" {
        with(Any()) {
            hash(this) shouldBe hash(this)
        }
        hash(DummyCompareUsingFields()) shouldBe hash(DummyCompareUsingFields())
        hash(DummyCompareUsingFields(null)) shouldBe hash(DummyCompareUsingFields(null))
        hash(DummyCompareUsingFields(null, null)) shouldBe hash(DummyCompareUsingFields(null, null))
        hash(1, 2, 3) shouldBe hash(1, 2, 3)
    }
})
