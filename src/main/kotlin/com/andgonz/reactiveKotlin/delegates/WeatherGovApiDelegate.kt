package com.andgonz.reactiveKotlin.delegates

import kotlinx.coroutines.*
import okhttp3.*
import org.springframework.stereotype.Component
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Component
class WeatherGovApiDelegate {

    private val client = OkHttpClient()
    private val url = "https://api.weather.gov/gridpoints/MLB/33,70/forecast"

    suspend fun pull() : Deferred<String> = withContext(Dispatchers.IO) {
        async {
            suspendCoroutine<String> { continuation ->
                val request = Request.Builder()
                    .url(url)
                    .build()


                client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val body =  response.body!!.string()
                        continuation.resume(body)
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        println("error")
                        println(call)
                        continuation.resumeWithException(e) // fails with exception
                    }
                })
            }
        }
    }


}