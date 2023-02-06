import kotlinx.coroutines.*
import okhttp3.*
import org.json.*

suspend fun getFantasyPremierLeagueData(): JSONArray? {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://fantasy.premierleague.com/api/bootstrap-static/")
        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.19 Safari/537.36")
        .build()
    client.newCall(request).execute().use { response ->
        return if (response.isSuccessful) {
            val body = response.body()
            val jsonObject = JSONObject(body!!.string())
            val elements = jsonObject.getJSONArray("elements")
            elements
        } else {
            null
        }
    }
}

fun main() {
    runBlocking {
        val data = getFantasyPremierLeagueData()
        if (data != null) {
            println(data)
        } else {
            println("Request failed")
        }
    }
}
