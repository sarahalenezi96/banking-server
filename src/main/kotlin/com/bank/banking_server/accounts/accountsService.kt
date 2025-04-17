package com.bank.banking_server.accounts

import com.bank.banking_server.exceptions.AccountLimitExceededException
import com.bank.banking_server.users.UserRepository
import com.bank.banking_server.transactions.TransactionService
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepo: AccountRepository,
    private val userRepo: UserRepository,
    private val transactionService: TransactionService
) {
    fun createAccount(request: CreateAccountRequest): Account {
        val user = userRepo.findById(request.userId).orElseThrow { Exception("User not found") }
        val existingAccounts = accountRepo.findAllByUserId(user.id)

        if (existingAccounts.size >= 5) {
            throw AccountLimitExceededException("User can't have more than 5 accounts")
        }

        val account = Account(
            accountNumber = request.accountNumber,
            name = request.name,
            balance = request.initialBalance,
            user = user
        )

        return accountRepo.save(account)
    }

    fun listAccounts(): List<Account> {
        return accountRepo.findAll()
    }

    fun closeAccount(accountNumber: String) {
        val account = accountRepo.findByAccountNumber(accountNumber)
            ?: throw Exception("Account not found")
        account.isActive = false
        accountRepo.save(account)
    }

    fun transfer(sourceAccountNumber: String, destinationAccountNumber: String, amount: Double): Double {
        val source = accountRepo.findByAccountNumber(sourceAccountNumber)
            ?: throw Exception("Source account not found")
        val destination = accountRepo.findByAccountNumber(destinationAccountNumber)
            ?: throw Exception("Destination account not found")

        return transactionService.transfer(source, destination, amount)
    }
}
