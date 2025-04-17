package com.bank.banking_server.transactions

import com.bank.banking_server.accounts.Account
import com.bank.banking_server.exceptions.InsufficientFundsException
import com.bank.banking_server.accounts.AccountRepository
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionRepo: TransactionRepo,
    private val accountRepo: AccountRepository
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

    fun getHistory(accountNumber: String): List<Transaction> {
        val account = accountRepo.findByAccountNumber(accountNumber)
            ?: throw InsufficientFundsException("Account not found")

        if (account.balance <= 0.0) {
            throw InsufficientFundsException("insufficient balance")
        }

        return transactionRepo.findByAccountNumber(accountNumber)
    }

    fun transfer(source: Account, destination: Account, amount: Double): Double {
        if (!source.isActive || !destination.isActive) {
            throw Exception("Cannot transfer from or to a closed account")
        }

        if (amount <= 0.0) {
            throw Exception("Amount must be positive")
        }

        if (source.balance < amount) {
            throw InsufficientFundsException("Insufficient funds")
        }

        source.balance -= amount
        destination.balance += amount

        accountRepo.save(source)
        accountRepo.save(destination)

        log(source.accountNumber, "TRANSFER_OUT", amount)
        log(destination.accountNumber, "TRANSFER_IN", amount)

        return source.balance
    }
}
