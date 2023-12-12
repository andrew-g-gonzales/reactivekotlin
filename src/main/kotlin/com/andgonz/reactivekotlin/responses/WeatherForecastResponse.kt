package com.andgonz.reactivekotlin.responses

data class WeatherForecastResponse<WeatherForecast>(
    val daily: List<WeatherForecast>
)

