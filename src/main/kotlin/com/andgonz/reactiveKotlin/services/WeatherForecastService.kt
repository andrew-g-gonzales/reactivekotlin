package com.andgonz.reactiveKotlin.services

import com.andgonz.reactiveKotlin.delegates.WeatherGovApiDelegate
import com.andgonz.reactiveKotlin.models.WeatherForecast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.springframework.beans.factory.annotation.Autowired
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import com.andgonz.reactiveKotlin.utils.DateUtil.Companion.getCurrentDateStr
import com.andgonz.reactiveKotlin.utils.DateUtil.Companion.getDayOfWeekStr
import org.springframework.stereotype.Component
import java.math.RoundingMode
import java.text.DecimalFormat

import java.util.Calendar

@Component
class WeatherForecastService {

    @Autowired
    lateinit var weatherApiDelegate: WeatherGovApiDelegate

    suspend fun getForecast(): Deferred<List<WeatherForecast>> = withContext(Dispatchers.IO) {

        val responseStr: String = weatherApiDelegate.pull().await()

        async {
            suspendCoroutine { continuation ->

                val gson = Gson()
                val mapAdapter = gson.getAdapter(object: TypeToken<Map<String, Any?>>() {})
                val model: Map<String, Any?> = mapAdapter.fromJson(responseStr)

                val cal = Calendar.getInstance()
                val (dateStr, dayOfWkStr) = Pair(
                    getCurrentDateStr(cal),
                    getDayOfWeekStr(cal)
                )

                @Suppress("UNCHECKED_CAST")
                val properties = model["properties"] as Map<String, Any?>
                @Suppress("UNCHECKED_CAST")
                val props = properties["periods"] as List<Map<String, Any?>>

                val todaysMaxTempRecord = props.filter {
                    (it["startTime"] as String).contains(dateStr)
                }.maxByOrNull { p -> p["temperature"] as Double }!!

                val temp = (((todaysMaxTempRecord.get("temperature") as Double)-32) * 5/9)
                val df = DecimalFormat("##.#")
                df.roundingMode = RoundingMode.DOWN
                val celsuisTemp = df.format(temp).toFloat()
                val blurb:String = todaysMaxTempRecord["shortForecast"] as String

                val forecastRtrnList = listOf(
                    WeatherForecast(dayOfWkStr,
                        celsuisTemp,
                        blurb)
                )
                continuation.resume(
                    forecastRtrnList
                )
            }
        }
    }
}