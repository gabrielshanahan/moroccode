package moroccode

internal class Dummy(private val f1: String? = "Hello", private val f2: Number? = 5) {
    override fun hashCode() = hash(f1, f2)
    override fun equals(other: Any?): Boolean = compareByFields(other) { listOf(f1, f2) }
}
