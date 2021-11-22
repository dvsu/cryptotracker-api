package com.david.cryptotracker.services

import java.sql.Timestamp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import com.david.cryptotracker.models.Transaction
import com.david.cryptotracker.repositories.TransactionRepository

@Service
class TransactionService(val transactionRepository: TransactionRepository) {

    @Autowired
    private var jdbcTemplate: JdbcTemplate? = null

    fun getTransactions(): List<Transaction> {
        return jdbcTemplate!!.query(
            """SELECT * FROM transactions""",
            BeanPropertyRowMapper.newInstance(Transaction::class.java))
    }

    fun addTransaction(transaction: Transaction) {
        jdbcTemplate!!.update(
            """
                INSERT INTO 
                    transactions(transaction_id, datetime, utc_datetime, amount) 
                VALUES(?, ?, ?, ?)
            """.trimIndent(),
            transaction.transaction_id,
            transaction.datetime,
            Timestamp.from(transaction.utc_datetime),
            transaction.amount)
    }
}
