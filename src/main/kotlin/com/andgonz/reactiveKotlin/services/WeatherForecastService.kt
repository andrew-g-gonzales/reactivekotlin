package com.andgonz.reactiveKotlin.services

import com.andgonz.reactiveKotlin.models.WeatherForecast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.andgonz.reactiveKotlin.utils.DateUtil.Companion.getCurrentDateStr
import com.andgonz.reactiveKotlin.utils.DateUtil.Companion.getDayOfWeekStr
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

@Component
class WeatherForecastService {
    suspend fun getForecast(): Flux<List<WeatherForecast>> {

        val responseStr: String = WebClient
            .create("https://api.weather.gov/gridpoints/MLB/33,70/forecast")
            .get()
            .retrieve().bodyToMono(String::class.java)
            .awaitFirst()

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

        val forecastList = Arrays.asList(WeatherForecast(dayOfWkStr,
                    celsuisTemp,
                    blurb))

        return Flux.just(forecastList)
    }
}