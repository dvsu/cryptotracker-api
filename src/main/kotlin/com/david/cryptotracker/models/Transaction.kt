package com.david.cryptotracker.models

import com.david.cryptotracker.utils.DateTimeConverter
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @Column(name = "transaction_id")
    var transaction_id: String? = null,
    @Column(name = "datetime")
    var datetime: String? = null,
    @Column(name = "utc_datetime")
    var utc_datetime: Instant? = null,
    @Column(name= "amount")
    var amount: Double = 0.0
)