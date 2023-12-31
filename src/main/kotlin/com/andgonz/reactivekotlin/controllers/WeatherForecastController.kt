package com.andgonz.reactivekotlin.controllers

import com.andgonz.reactivekotlin.models.WeatherForecast
import com.andgonz.reactivekotlin.services.WeatherForecastService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class WeatherForecastController {
    @Autowired
    lateinit var weatherForecastService: WeatherForecastService
    @GetMapping("/forecast", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getForecast(): List<WeatherForecast> {
        return  weatherForecastService.getForecast().awaitFirst()
    }
}