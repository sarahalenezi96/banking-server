package com.bank.banking_server.accounts

import com.bank.banking_server.users.UserRepository
import com.bank.banking_server.transactions.TransactionService
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(
    private val accountRepo: AccountRepository,
    private val userRepo: UserRepository,
    private val transactionService: TransactionService
) {
    fun createAccount(userId: Long, name: String, initialBalance: Double): Account {
        val user = userRepo.findById(userId).orElseThrow { Exception("User not found") }
        val account = Account(
            accountNumber = UUID.randomUUID().toString(),
            name = name,
            balance = initialBalance,
            user = user
        )
        return accountRepo.save(account)
    }

    fun listAccounts(): List<Account> = accountRepo.findAll()

    fun closeAccount(accountNumber: String) {
        val account = accountRepo.findByAccountNumber(accountNumber) ?: throw Exception("Account not found")
        account.isClosed = true
        accountRepo.save(account)
    }

    fun transfer(sourceAccountNumber: String, destinationAccountNumber: String, amount: Double): Double {
        if (amount <= 0.0) throw Exception("Amount must be positive")

        val source = accountRepo.findByAccountNumber(sourceAccountNumber) ?: throw Exception("Source account not found")
        val destination = accountRepo.findByAccountNumber(destinationAccountNumber) ?: throw Exception("Destination account not found")

        if (source.isClosed || destination.isClosed) throw Exception("Cannot transfer from/to a closed account")
        if (source.balance < amount) throw Exception("Insufficient balance")

        source.balance -= amount
        destination.balance += amount

        accountRepo.save(source)
        accountRepo.save(destination)

        transactionService.log(source.accountNumber, "TRANSFER", amount)
        transactionService.log(destination.accountNumber, "TRANSFER", amount)

        return source.balance
    }
}
