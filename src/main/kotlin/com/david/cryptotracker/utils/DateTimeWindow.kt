package com.david.cryptotracker.utils

import java.time.Instant
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class DateTimeWindow {

    companion object {
        fun shiftByOneHour(datetime: Instant): Instant {
            return datetime.atZone(ZoneOffset.UTC)
                .withMinute(0)
                .withSecond(0)
                .toInstant()
                .plus(1.toLong(), ChronoUnit.HOURS)
        }
    }
}