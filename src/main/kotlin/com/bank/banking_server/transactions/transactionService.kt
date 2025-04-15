package com.bank.banking_server.transactions

import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionRepo: TransactionRepo
) {
    fun log(accountNumber: String, transactionType: String, amount: Double) {
        val transaction = Transaction(
            accountNumber = accountNumber,
            type = transactionType,
            amount = amount
        )
        transactionRepo.save(transaction)
    }

    fun listTransactions(): List<Transaction> = transactionRepo.findAll()

    // Add this method clearly to fix your error:
    fun getHistory(accountNumber: String): List<Transaction> {
        return transactionRepo.findByAccountNumber(accountNumber)
    }
}
