package com.david.cryptotracker.models

import javax.persistence.*

data class Balance(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: String? = null,
    var startDatetime: String? = null,
    var endDatetime: String? = null,
    var amount: Double? = null
)
