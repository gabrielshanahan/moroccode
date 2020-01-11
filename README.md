# Moroccode

Moroccode is a small Kotlin utility to help you create easy and bug-free hash codes and equality checks. It is
inspired by, and aims to improve, [HashKode](https://github.com/PvdBerg1998/HashKode).

The functionality of both libraries is comparable, with Moroccode achieving slightly better performance in some cases, 
and being more concise in others. It is currently among the [smallest](#comparison-to-alternative-libraries),
[fastest](#comparison-to-alternative-libraries) and [most concise](#comparison-to-alternative-libraries) of such 
libraries. 

## Why?
Mainly because [HashKode](https://github.com/PvdBerg1998/HashKode) is 
[no longer being maintained](https://github.com/PvdBerg1998/HashKode/issues/3#issuecomment-553642518).

From [HashKode](https://github.com/PvdBerg1998/HashKode#why):
> Java identifies objects by their `hashcode` and their `equals` method. If you do not override these methods, the JVM 
>can only check objects for referential equality, not structural equality. 
>([Read more](https://kotlinlang.org/docs/reference/equality.html#equality))

> Kotlin generates these methods [automatically](https://kotlinlang.org/docs/reference/data-classes.html#data-classes) 
>when you create a `data class`, but sometimes this is not preferred.

> Implementing `hashcode` and `equals` can be tedious, verbose and is bug prone. HashKode provides concise ways to 
>override these methods.

## Features
- Lightweight: 8 KB*
- [Fast](#comparison-to-alternative-libraries)
- [Expressive](#how-to-use)
- Uses the standard library to compute hash codes: tested, high quality hashes
- Able to list differences

---
  
# Using Moroccode
Moroccode can be downloaded from the [Maven Central Repository]().

Using Gradle with Kotlin DSL:
```Kotlin
implementation("io.github.gabrielshanahan", "moroccode", "1.0.0")
```

Using Gradle with Groovy:
```Groovy
implementation 'io.github.gabrielshanahan:moroccode:1.0.0'
```

Using Maven
```XML
<dependency>
  <groupId>io.github.gabrielshanahan</groupId>
  <artifactId>moroccode</artifactId>
  <version>1.0.0</version>
  <type>pom.sha512</type>
</dependency>
```



Moroccode is released under the [MIT license](LICENSE.md).
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
`hash` uses Java's [Objects.hashCode](https://docs.oracle.com/javase/7/docs/api/java/util/Objects.html#hashCode%28java.lang.Object%29)
and Kotlin's [Array.contentHashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/content-hash-code.html) 
to calculate the hashes.

---

### Equality
Checking two objects for structural equality can be done by overriding the builtin `equals` method and using Moroccode's
comparison functions.

#### compareUsingFields - The concise way

Using field names in a lambda with receiver:
```Kotlin
override fun equals(other: Any?) = compareUsingFields(other) { fields { field1 } }
override fun equals(other: Any?) = compareUsingFields(other) { fields { field1 } and { field2 } and ... }
```

Using getters:
```Kotlin
override fun equals(other: Any?) = compareUsingFields(other) { fields(MyClass::field1) }
override fun equals(other: Any?) = compareUsingFields(other) { fields(MyClass::field1) and MyClass::field2 and ... }
```  

That's it.

#### compareUsing - The efficient way

```Kotlin
override fun equals(other: Any?): Boolean = compareUsing(other) { field1 == it.field1 && field2 == it.field2 && ... }
```

This method allows custom equality definitions - there is nothing stopping you from writing `field1 == it.field1 || field2 == it.field2`

---

### Difference
Moroccode can list differences between fields of objects. The API is the same as with [compareByFields](#comparebyfields---the-concise-way).
```kotlin
val test1 = Dummy(f1 = "Hello", f2 = 5)
val test2 = Dummy(f1 = "World", f2 = 5)

val diff = test1.diffByFields(test2) { listOf(f1, f2) }
```
`diff` will now contain a single `FieldDifference` object, since one different field was found.
`FieldDifference` is a data class containing two pairs of the form `object -> object.field_value` - one for the receiver 
and one for the argument of diffByFields.

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

- [HashKode](https://github.com/PvdBerg1998/HashKode) (14.2 KB: +6.2 KB, **no longer maintained**)
- [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/EqualsBuilder.html) (483 KB: +475 KB)

## Comparison to alternative libraries
We compare implementing `equals` using Moroccode to achieving the same with the following:
- [HashKode](https://github.com/PvdBerg1998/HashKode)
   - [compareUsing](https://github.com/PvdBerg1998/HashKode#regular-x--y) (most efficient)
   - [compareFields](https://github.com/PvdBerg1998/HashKode#comparefield) (most concise)
- [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/EqualsBuilder.html)
- [nikarh/equals-builder](https://github.com/nikarh/equals-builder)
- [consoleau/kassava](https://github.com/consoleau/kassava)

### Results
| Library name                                   | # of characters*         | Speed                     | Size   |
|------------------------------------------------|--------------------------|---------------------------|--------|
| Moroccode - compareUsing                       | 83                       | **23.929 ± 0.511 ns/op**  | 8 KB   |
| Moroccode - compareUsingFields using getters   | 132 (75 from class name) |                           | 8 KB   |
| Moroccode - compareUsingFields using fields    | **55**                   | 28.784 ± 0.803 ns/op      | 8 KB   |
| HashKode - compareFields (no longer maintained)| 177 (75 from class name) | 26.538 ± 0.550 ns/op      | 15 KB  |
| HashKode - compareUsing  (no longer maintained)| 115                      | 23.963 ± 1.054 ns/op      | 15 KB  |
| Apache Commons Lang                            | 157 (15 from class name) | 28.679 ± 0.361 ns/op      | 492 KB |
| nikarh/equals-builder                          | 186 (75 from class name) | 26.706 ± 0.200 ns/op      | 4 KB   |
| consoleau/kassava                              | 135 (75 from class name) | 131.282 ± 2.324 ns/op     | 5 KB   |

\* Characters where counted as number of characters necessary to implement the equals method, excluding whitespace and a 
single count of every fields name, since this is by definition a necessary minimum every implementation must have (in 
this instances, the field names where anInt, aString, aDouble, aBool and aList, for a total of 29 characters. See
[the benchmark](https://github.com/gabrielshanahan/moroccode_jmh/blob/master/src/main/kotlin/BenchmarkObject.kt) for the
code that was used.

If the implementation needed to use the class name, typically when referencing getters, it is noted (in this case, the 
class was BenchmarkObject, which is 15 characters long).  

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
    - List<String> (`["A", "B", "C", "D", "E"]`)
- Benchmark implemented using [JMH](https://openjdk.java.net/projects/code-tools/jmh/) with 
[default settings](https://github.com/openjdk/jmh/blob/master/jmh-core/src/main/java/org/openjdk/jmh/runner/Defaults.java).
- The entire code is available [here](https://github.com/gabrielshanahan/moroccode_jmh/)
