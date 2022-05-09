## Differs on JVM and Native:
```Kotlin
stringBuilder.append(charArray, 0, 1)
```

### Reproduce:
`./gradlew jvmTest` - success test  
`./gradlew clean nativeTest -i` fail test

### fail logs:
kotlin.AssertionError: Expected `a`, actual `kotlin.CharArray@69ba8801`  

### My point on view
1) expect class StringBuilder have function:
```kotlin
expect class StringBuilder {
    fun append(value: CharSequence?, startIndex: Int, endIndex: Int): StringBuilder {
    //...
```
2) And we have extension function:
```kotlin
fun StringBuilder.append(vararg value: Any?): StringBuilder
```
I think there are problems with kotlin jvm compiler  
CharArray is not a CharSequence  
But ```stringBuilder.append(charArray, 0, 1)``` uses ```fun append(value: CharSequence?, Int, Int)```  
So, we have unpredictable behaviour, and it **differs on JVM and Native compiler**.

### My solution
**It's hard to change JVM behaviour, because some project's already use it!**
(like [androidx Compose](https://github.com/JetBrains/androidx/blob/4f18b0bddfaf20f4f2cea2b61248cebcf0afc3be/compose/ui/ui-text/src/commonMain/kotlin/androidx/compose/ui/text/input/GapBuffer.kt#L191))   
But, we can create another extension function:  
```kotlin
public fun StringBuilder.append(charArray: CharArray, startIndex: Int, endIndex:Int): StringBuilder {
    // implement code, that will work same on JVM, Native, JS, WASM
}
```
So, after it, this code will work the same way on all platforms, and we don't need to change JVM behaviour

