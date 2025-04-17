package com.bank.banking_server.transactions

import com.bank.banking_server.exceptions.InsufficientFundsException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
class TransactionController(private val transactionService: TransactionService) {

    @GetMapping("/{accountNumber}")
    fun getTransactions(@PathVariable accountNumber: String): List<Transaction>? {
        return try {
            transactionService.getHistory(accountNumber)
        } catch (error: InsufficientFundsException) {
            println(error.message)
            null
        } catch (error: Exception) {
            println(error.message)
            null
        }
    }
}
