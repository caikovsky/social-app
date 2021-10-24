package life.league.challenge.kotlin.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import java.io.FileNotFoundException
import java.net.URL

fun readFile(path: String): String {
    val content: URL? = ClassLoader.getSystemResource(path)

    return content?.readText() ?: throw FileNotFoundException("file path: $path was not found")
}

inline fun <reified T> parse(gson: Gson, path: String): T {
    val file = readFile("$path.json")
    return gson.fromJson(file, object : TypeToken<T>() {}.type)
}

inline fun <reified T> Gson.parseFrom(path: String): T = parse(this, path)

infix fun Any.shouldBe(expected: Any) = Assert.assertEquals(expected, this)