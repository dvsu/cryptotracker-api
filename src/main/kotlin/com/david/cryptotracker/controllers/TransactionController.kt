package com.david.cryptotracker.controllers

import java.util.*
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.web.bind.annotation.*
import com.david.cryptotracker.models.Transaction
import com.david.cryptotracker.services.TransactionService
import com.david.cryptotracker.utils.DateTimeConverter
import org.springframework.http.ResponseEntity
import com.david.cryptotracker.models.Success


@EnableBinding(Source::class)
@RestController
@RequestMapping("api")
class TransactionController(val service: TransactionService, val pubSubTemplate: PubSubTemplate, val source: Source) {

    // Publish to this PubSub topic
    val publishedTopic = "testtopic"

    @GetMapping("/transactions")
    fun getTransactions(): List<Transaction> = service.getTransactions()

    @PostMapping("/transaction")
    fun addTransaction(@RequestBody transaction: Transaction): ResponseEntity<Any> {
        transaction.utc_datetime = DateTimeConverter.zonedStringToUtc(transaction.datetime)
        transaction.transaction_id = UUID.randomUUID().toString()

        // publish message to PubSub for other implementation
        pubSubTemplate.publish(publishedTopic, transaction)

        // add new transaction data to Cloud SQL
        service.addTransaction(transaction)
        return ResponseEntity.ok(Success("Update successful"))
    }
}
