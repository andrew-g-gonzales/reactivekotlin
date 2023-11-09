package com.andgonz.reactiveKotlin.utils

import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DateUtil {
    companion object {
        private val dateFormated = SimpleDateFormat("yyyy-MM-dd")

        fun getCurrentDateStr(): String {
            return dateFormated
                .format(Calendar.getInstance().timeInMillis)

        }
    }
}