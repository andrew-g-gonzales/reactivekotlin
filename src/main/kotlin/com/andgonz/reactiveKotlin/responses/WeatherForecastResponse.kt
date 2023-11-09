package com.andgonz.reactiveKotlin.responses

import com.andgonz.reactiveKotlin.models.WeatherForecast

data class WeatherForecastResponse<WeatherForecast>(
    val daily: List<WeatherForecast>
)

