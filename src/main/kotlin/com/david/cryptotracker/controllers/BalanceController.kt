package com.david.cryptotracker.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.david.cryptotracker.models.TimeRange
import com.david.cryptotracker.services.BalanceService

@RestController
@RequestMapping("api")
class BalanceController(val service: BalanceService) {

    @GetMapping("/balance")
    fun getBalance(@RequestBody timeRange: TimeRange): List<Any> {
        return service.getBalanceBetweenTimeRange(timeRange)
    }
}