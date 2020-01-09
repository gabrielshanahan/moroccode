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
 * One of two convenience methods to test for equality. Objects are equal if they have the same type and the list of
 * fields returned by [getFields] are the same. This method is more concise, but less efficient, than [compareUsing]
 *
 * @receiver Object to compare to [other]
 * @param other Object to compare to receiver
 * @param fields Function literal that returns the fields to be compared
 *
 * @see Any.equals
 */
public inline fun <reified T : Any> T.compareByFields(
        other: Any?,
        fields: EqualContext<T>.() -> Boolean
): Boolean = other is T && EqualContext(this, other).fields()

/**
 * One of two convenience methods to test for equality. Objects are equal if they have the same type and [compare]
 * returns true. This method requires some boilerplate and is less concise, but more efficient, than [compareByFields].
 *
 * @receiver Object to compare to [other]
 * @param other Object to compare to receiver
 * @param compare Function literal that does the comparison. One object is its receiver, the other its parameter.
 *
 * @see Any.equals
 */
public inline fun <reified T : Any> T.compareUsing(
    other: Any?,
    compare: T.(T) -> Boolean
): Boolean = other is T && compare(other)
