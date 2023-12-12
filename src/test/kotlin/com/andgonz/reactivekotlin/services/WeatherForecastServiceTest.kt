package com.andgonz.reactivekotlin.services

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class WeatherForecastServiceTest {

    @Test
    fun `test service`():Unit = runBlocking {

        val weatherForecastService:WeatherForecastService = WeatherForecastService()
        val forecastList = weatherForecastService.getForecast().awaitFirst()
        forecastList.forEach {

            println(it)
        }

    }
}