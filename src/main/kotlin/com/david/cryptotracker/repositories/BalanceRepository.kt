package com.david.cryptotracker.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.david.cryptotracker.models.Balance

@Repository
interface BalanceRepository: CrudRepository<Balance, Long>