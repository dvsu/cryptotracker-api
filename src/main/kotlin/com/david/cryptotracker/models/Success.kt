package com.david.cryptotracker.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

class Success (val message: String) {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val timestamp: Date = Date()
}

