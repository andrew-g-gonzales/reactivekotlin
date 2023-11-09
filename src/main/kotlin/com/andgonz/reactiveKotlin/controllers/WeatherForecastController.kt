package com.andgonz.reactiveKotlin.controllers

import com.andgonz.reactiveKotlin.models.WeatherForecast
import com.andgonz.reactiveKotlin.repositories.UserRepository
import com.andgonz.reactiveKotlin.responses.WeatherForecastResponse
import com.andgonz.reactiveKotlin.services.WeatherForecastService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Arrays


@RestController
class WeatherForecastController {


    @GetMapping("/forecast", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getForecast():WeatherForecastResponse<WeatherForecast>{


        return WeatherForecastResponse(
            listOf(
            WeatherForecast("Thursday",
                27.2f,
                "Partly Sunny")
        )
        )
    }


}