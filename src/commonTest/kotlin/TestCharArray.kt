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

}
