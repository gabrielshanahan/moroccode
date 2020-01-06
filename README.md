# Moroccode

Moroccode is a small Kotlin utility to help you create easy and bug-free hash codes and equality checks. It is
inspired by, and aims to improve, [HashKode](https://github.com/PvdBerg1998/HashKode).

The functionality of both libraries is comparable, with Moroccode achieving slightly better performance in some cases, 
and being slightly more concise in others.

## Why?
Because [HashKode](https://github.com/PvdBerg1998/HashKode) is [no longer being maintained](https://github.com/PvdBerg1998/HashKode/issues/3#issuecomment-553642518).

From [HashKode](https://github.com/PvdBerg1998/HashKode#why):
> Java identifies objects by their `hashcode` and their `equals` method. If you do not override these methods, the JVM can only check objects for referential equality, not structural equality. ([Read more](https://kotlinlang.org/docs/reference/equality.html#equality))

> Kotlin generates these methods [automatically](https://kotlinlang.org/docs/reference/data-classes.html#data-classes) when you create a `data class`, but sometimes this is not preferred.

> Implementing `hashcode` and `equals` can be tedious, verbose and is bug prone. HashKode provides concise ways to override these methods.

## Features
- Lightweight: **6.4 KB**
- [Very fast](#benchmarks)
- [Expressive](#how-to-use)
- Uses the standard library to compute hash codes: tested, high quality hashes
- Able to list differences

---
  
# Using Moroccode
Moroccode can be downloaded from the [Maven Central Repository]().

Moroccode is released under the [MIT license](LICENSE.md).

## Alternatives
As of Q4 2019:
- [HashKode](https://github.com/PvdBerg1998/HashKode) (14.2 KB: +7.8 KB, **no longer maintained**)
- [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/HashCodeBuilder.html) (483 KB: +468.8 KB)
- [Google Guava](https://github.com/google/guava/wiki/CommonObjectUtilitiesExplained) (55 KB: +40.8 KB)

---

## How to use
- [Hash codes](#hash-codes)
- [Equality](#equality)
- [Difference](#difference)

---

### Hash codes
Moroccode makes creating hashes of your fields extremely convenient: just pass your fields to `hash`.
```kotlin
override fun hashCode() = hash(
    field1, field2, field3, field4
)
```
Unlike HashKode, `hash` uses Java's [Objects.hashCode](https://docs.oracle.com/javase/7/docs/api/java/util/Objects.html#hashCode%28java.lang.Object%29) and Kotlin's [Array.contentHashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/content-hash-code.html) to calculate the hashes.

---

### Equality
Checking two objects for structural equality can be done by overriding the builtin `equals` method and using Moroccode's comparison functions.

#### compareByFields - The concise way

```Kotlin
override fun equals(other: Any?) = compareByFields(other) { listOf(field1, field2, field3, ...) }
``` 

That's it.

#### compareUsing - The efficient way

```Kotlin
override fun equals(other: Any?): Boolean = compareUsing(other) { field1 == it.field1 && field2 == it.field2 && ... }
```

This method requires some boilerplate, but it performs much faster. It also allows custom equality definitions - there is nothing stopping you from writing `field1 == it.field1 || field2 == it.field2`

---

### Difference
Moroccode can list differences between fields of objects. The API is the same as with [compareByFields](#comparebyfields---the-concise-way).
```kotlin
val test1 = Dummy(f1 = "Hello", f2 = 5)
val test2 = Dummy(f1 = "World", f2 = 5)

val diff = test1.diffByFields(test2) { listOf(f1, f2) }
```
`diff` will now contain a single `FieldDifference` object, since one different field was found.
`FieldDifference` is a data class containing two pairs of the form `object -> object.field_value` - one for the receiver and one for the argument of diffByFields.

A custom comparison can be done by using `diffByFields`.

```kotlin
val test1 = Dummy(f1 = "Hello", f2 = 5)
val test2 = Dummy(f1 = "World", f2 = 5)

val diffSame = test1.diffBy(test2) {
    if (f1.length != it.f1.length) listOf(f1 to it.f1) else emptyList()
}
```

The lambda returns a list of all differing pairs.

---

## Benchmarks
Moroccode can outperform alternative libraries.

### Benchmark setup
- MacBook Pro (13-inch, 2018)
- 2,3 GHz Quad-Core Intel Core i5
- 16 GB 2133 MHz LPDDR3
- macOS Catalina 10.15.2 (19C57)

### Benchmark details
- Object with 5 fields:
    - Integer (index)
    - String (`Hello World`)
    - Double (`Math.PI`)
    - Boolean (`true`)
    - Array<String> (`["A", "B", "C", "D", "E"]`)
- Asserted equals check repeated 50.000.000
- Test ran 3 times
- Libraries tested: `Moroccode`, `HashKode`, `Guava`, `Apache`

### compareByFields
#### TBD

### compareUsing
#### TBD
