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

/**
 * A container for functions used in [compareUsingFields]. These helper functions define a simple DSL syntax for
 * equality checks, either in the form: fields { field1 } and { field2 } ..., or in using explicit getters:
 * field(MyObject::field1) and MyObject::Field2 ...
 *
 * @param T The type of the objects being compared
 * @property one One of the objects being compared
 * @property two The other object being compared
 *
 * @sample io.github.gabrielshanahan.moroccode.DummyCompareUsingSingleField.equals
 * @sample io.github.gabrielshanahan.moroccode.DummyCompareUsingFields.equals
 *
 * @see compareUsingFields
 */
public class CompareUsingFieldsContext<T : Any>(val one: T, val two: T) {
    /**
     * Use the passed in [getter] to create an equality check.
     */
    inline fun <R> fields(getter: T.() -> R) = one.getter() == two.getter()

    /**
     * Used to chain additional equality checks
     */
    inline infix fun <R> Boolean.and(crossinline getter: T.() -> R): Boolean =
            this && one.getter() == two.getter()
}
