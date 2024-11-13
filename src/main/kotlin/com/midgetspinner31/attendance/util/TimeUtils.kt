package com.midgetspinner31.attendance.util

import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.WeekFields

object TimeUtils {
    fun getStartOfWeek(date: OffsetDateTime): OffsetDateTime {
        return date.with(WeekFields.ISO.dayOfWeek(), 1)
            .truncatedTo(ChronoUnit.DAYS)
    }

    fun getStartOfWeek(): OffsetDateTime {
        return getStartOfWeek(OffsetDateTime.now())
    }
}
