package com.david.cryptotracker.services

import java.sql.Timestamp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import com.david.cryptotracker.models.Asset
import com.david.cryptotracker.models.TimeRange
import com.david.cryptotracker.models.Transaction
import com.david.cryptotracker.repositories.BalanceRepository
import com.david.cryptotracker.utils.DateTimeConverter
import com.david.cryptotracker.utils.DateTimeWindow

@Service
class BalanceService(val balanceRepository: BalanceRepository) {

    @Autowired
    private var jdbcTemplate: JdbcTemplate? = null

    fun getBalanceBetweenTimeRange(timeRange: TimeRange): List<Asset> {

        val accumulatedAsset: MutableList<Asset> = mutableListOf()

        val start = DateTimeConverter.zonedStringToUtc(timeRange.startDatetime)
        val end = DateTimeConverter.zonedStringToUtc(timeRange.endDatetime)
        val queriedData = jdbcTemplate!!.query("select * from transactions where utc_datetime < ?",
            BeanPropertyRowMapper.newInstance(Transaction::class.java),
            Timestamp.from(end)
        )

        val sortedRangedData = queriedData.sortedWith(compareBy { it.utc_datetime })

        val lowerBorder = DateTimeConverter.instantRoundDownToNearestHour(start!!)
        val upperBorder = DateTimeConverter.instantRoundUpToNearestHour(end!!)

        val history = sortedRangedData
            .filter { it.utc_datetime!! < lowerBorder }
            .map { it.amount }
            .fold(0.0) { acc, amount -> acc + amount!! }

        var accu = history
        var cursorStart = lowerBorder
        var cursorLimit = DateTimeWindow.shiftByOneHour(lowerBorder)

        while (cursorLimit <= upperBorder) {
            accu += sortedRangedData
                .filter { it.utc_datetime!! >= cursorStart && it.utc_datetime!! < cursorLimit }
                .map { it.amount }
                .fold(0.0) { acc, amount -> acc + amount!! }

            accumulatedAsset.add(
                Asset(
                    datetime=DateTimeConverter.instantToDateTimeString(cursorLimit),
                    amount=accu))
            cursorStart = cursorLimit
            cursorLimit = DateTimeWindow.shiftByOneHour(cursorLimit)
        }
        return accumulatedAsset
    }
}
