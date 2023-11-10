package com.andgonz.reactiveKotlin.utils


import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        private val dateFormated = SimpleDateFormat("yyyy-MM-dd")
        private val dayOfWkFmt = SimpleDateFormat("EEEE", Locale.ENGLISH)

        fun getCurrentDateStr(cal: Calendar): String {
            return dateFormated
                .format(cal.time)
        }
        fun getDayOfWeekStr(cal: Calendar): String {
           return dayOfWkFmt.format(cal.timeInMillis)
        }
    }
}