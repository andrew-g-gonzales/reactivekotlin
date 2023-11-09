package com.andgonz.reactiveKotlin.utils

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class DateUtilsTest {

    @Test
    fun `test companion object`() {
        val currentDateStr = DateUtil.getCurrentDateStr()
        assertNotNull(currentDateStr)
    }
}