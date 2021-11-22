package com.david.cryptotracker.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.util.*

class CustomError(
    status: HttpStatus,
    val message: String
) {

    var statusCode: Int
    var statusName: String

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val timestamp: Date = Date()

    init {
        statusCode = status.value()
        statusName = status.name
    }
}