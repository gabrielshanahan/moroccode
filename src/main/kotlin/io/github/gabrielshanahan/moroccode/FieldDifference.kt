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

/**
 * Container class for field differences. The words "receiver" and "argument" are mean in the context of the difference
 * function [diffByFields] and [diffBy]
 *
 * @param receiverData A pair containing the receiver object and its value of the differing field
 * @param argumentData A pair containing the argument object and its value of the differing field
 */
data class FieldDifference<out T>(val receiverData: Pair<T, FieldValue>, val argumentData: Pair<T, FieldValue>)

/**
 * Returns a list of [FieldDifferences][FieldDifference] for every differing field of the receiver and argument.
 *
 * @receiver Object to compare to [other]
 * @param other Object to compare to receiver
 * @param getFields Function literal that returns the fields to be compared
 */
inline fun <reified T : Any> T.diffByFields(
    other: T,
    getFields: T.() -> List<FieldValue>
) = if (this === other) emptyList() else (getFields() zip other.getFields())
        .filter { it.first != it.second }
        .map {
            FieldDifference(this to it.first, other to it.second)
        }

/**
 * Returns a list of [FieldDifferences][FieldDifference] as specified by [getDiffs].
 *
 * @receiver Object to compare to [other]
 * @param other Object to compare to receiver
 * @param getDiffs Function literal that returns a list of field difference pairs, i.e.
 * listOf(receiver.field_1 to argument.field_1, ...)
 */
inline fun <reified T : Any> T.diffBy(
    other: T,
    getDiffs: T.(T) -> List<Pair<FieldValue, FieldValue>>
) = if (this === other) emptyList() else getDiffs(other).map {
    FieldDifference(this to it.first, other to it.second)
}
