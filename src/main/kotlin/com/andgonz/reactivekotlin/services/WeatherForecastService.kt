package com.andgonz.reactivekotlin.services

import com.andgonz.reactivekotlin.models.WeatherForecast
import com.andgonz.reactivekotlin.utils.DateUtil.Companion.getCurrentDateStr
import com.andgonz.reactivekotlin.utils.DateUtil.Companion.getDayOfWeekStr
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

@Component
class WeatherForecastService {

    suspend fun getForecast(): Flux<List<WeatherForecast>> {

        val model: Map<*, Any?>? = WebClient
            .create("https://api.weather.gov/gridpoints/MLB/33,70/forecast")
            .get()
            .retrieve().bodyToFlux(object: ParameterizedTypeReference<Map<String, Any?>> () {})
            .awaitFirst()

        val cal = Calendar.getInstance()
        val (dateStr, dayOfWkStr) = Pair(
            getCurrentDateStr(cal),
            getDayOfWeekStr(cal)
        )

        @Suppress("UNCHECKED_CAST")
        val properties = model?.get("properties") as Map<String, Any?>
        @Suppress("UNCHECKED_CAST")
        val props = properties["periods"] as List<Map<String, Any?>>

        val todaysMaxTempRecord = props.filter {
            (it["startTime"] as String).contains(dateStr)
        }.maxByOrNull { p -> (p["temperature"].toString().toDouble() )}!!

        val temp = (((todaysMaxTempRecord.get("temperature").toString().toDouble())-32) * 5/9).toDouble()
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