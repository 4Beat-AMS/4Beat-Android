package com.fourbeat.presentation.util

import android.os.Build
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

object DateProvider {
    fun today(): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now().toString()
        } else {
            val cal = Calendar.getInstance()
            String.format(
                Locale.KOREA,
                "%04d-%02d-%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH),
            )
        }
}
