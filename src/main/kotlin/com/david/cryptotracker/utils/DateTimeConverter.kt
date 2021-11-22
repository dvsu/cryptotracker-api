package com.david.cryptotracker.utils

import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DateTimeConverter {

    companion object {
        fun zonedStringToEpoch(strDatetime: String?): Long? {
            return if (strDatetime != null) ZonedDateTime
                .parse(strDatetime)
                .withZoneSameInstant(ZoneOffset.UTC)?.
                toInstant()?.
                toEpochMilli()
            else null
        }

        fun zonedStringToUtc(strDatetime: String?): Instant? {
            return if (strDatetime != null) ZonedDateTime
                .parse(strDatetime)
                .withZoneSameInstant(ZoneOffset.UTC)
                .toInstant()
            else null
        }

        fun dateTimeStringToInstant(strDatetime: String): Instant {
            return ZonedDateTime
                .parse(strDatetime)
                .withZoneSameInstant(ZoneOffset.UTC)
                .toInstant();
        }

        fun instantToDateTimeString(datetime: Instant): String {
            return datetime
                .atOffset(ZoneOffset.of("+00:00"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx"))
        }

        fun instantRoundDownToNearestHour(datetime: Instant): Instant {
            return datetime
                .atZone(ZoneOffset.UTC)
                .withMinute(0)
                .withSecond(0)
                .toInstant()
        }

        fun instantRoundUpToNearestHour(datetime: Instant): Instant {
            val increment = if (
                datetime.atZone(ZoneOffset.UTC).minute > 0 ||
                datetime.atZone(ZoneOffset.UTC).second > 0)
                1 else 0

            return datetime
                .atZone(ZoneOffset.UTC)
                .withMinute(0)
                .withSecond(0)
                .toInstant()
                .plus(increment.toLong(), ChronoUnit.HOURS)
        }
    }
}