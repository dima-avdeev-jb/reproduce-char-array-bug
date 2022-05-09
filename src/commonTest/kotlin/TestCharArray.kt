import kotlin.test.Test
import kotlin.test.assertEquals

class TestCharArray {

    @Test
    fun testCharArray() {
        val charArray = CharArray(1)
        charArray[0] = 'a'
        val stringBuilder = StringBuilder()
        stringBuilder.append(charArray, 0, 1)
        assertEquals("a", stringBuilder.toString())
    }

    @Test
    fun testCharArray2() {
        overload(CharArray(0), 1)
    }

}

fun overload(charSequence: CharSequence?, i:Int) {
    println("overload with CharSequence")
}

fun overload(vararg any: Any) {
    println("overload with Any")
}
