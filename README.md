## Differs on JVM and Native:
```Kotlin
stringBuilder.append(charArray, 0, 1)
```

### Reproduce:
`./gradlew jvmTest` - success test  
`./gradlew clean nativeTest -i` fail test

### fail logs:
kotlin.AssertionError: Expected `a`, actual `kotlin.CharArray@69ba8801`  

### My point of view
1) Java class StringBuilder have function:
```java
    @Override
    public StringBuilder append(**char[] str**, int offset, int len) {
        super.append(str, offset, len);
        return this;
    }
```
2) And kotlin have extension function:
```kotlin
    fun StringBuilder.append(vararg value: Any?): StringBuilder
```
Jvm Kotlin compiler take first function from Java  
Native Kotlin compiler  
So, we have unpredictable behaviour, and it **differs on JVM and Native compiler**.

### Possible solution
**It's hard to change JVM behaviour, because some project's already use it!**
(like [androidx Compose](https://github.com/JetBrains/androidx/blob/4f18b0bddfaf20f4f2cea2b61248cebcf0afc3be/compose/ui/ui-text/src/commonMain/kotlin/androidx/compose/ui/text/input/GapBuffer.kt#L191))   
But, we can create another extension function:  
```kotlin
public fun StringBuilder.append(charArray: CharArray, startIndex: Int, endIndex:Int): StringBuilder {
    // implement code, that will work same on JVM, Native, JS, WASM
}
```
So, after it, this code will work the same way on all platforms, and we don't need to change JVM behaviour

