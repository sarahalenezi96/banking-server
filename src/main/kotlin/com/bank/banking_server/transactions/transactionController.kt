package com.bank.banking_server.transactions

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
class TransactionController(private val transactionService: TransactionService) {

    @GetMapping("/{accountNumber}")
    fun getTransactions(@PathVariable accountNumber: String): Map<String, Any> {
        val transactions = transactionService.getHistory(accountNumber)
        return mapOf("transactions" to transactions)
    }
}
