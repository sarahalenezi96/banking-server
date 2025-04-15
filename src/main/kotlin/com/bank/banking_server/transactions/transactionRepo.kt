package com.bank.banking_server.transactions

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepo : JpaRepository<Transaction, Long> {
    fun findByAccountNumber(accountNumber: String): List<Transaction>
}
