package com.bank.banking_server.accounts

import com.bank.banking_server.exceptions.AccountLimitExceededException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(private val accountService: AccountService) {

    @GetMapping("/accounts")
    fun listAccounts(): List<Account> {
        return accountService.listAccounts()
    }

    @PostMapping
    fun createAccount(@RequestBody body: CreateAccountRequest): Any {
        return try {
            accountService.createAccount(body)
        } catch (error: AccountLimitExceededException) {
            "Too many accounts: ${error.message}"
        } catch (error: Exception) {
            "Account creation error: ${error.message}"
        }
    }

    @PostMapping("/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): String {
        return try {
            accountService.closeAccount(accountNumber)
            "Account closed successfully"
        } catch (error: Exception) {
            "Failed to close account: ${error.message}"
        }
    }

    @PostMapping("/transfer")
    fun transfer(@RequestBody body: TransferRequest): Any {
        return try {
            accountService.transfer(
                body.sourceAccountNumber,
                body.destinationAccountNumber,
                body.amount
            )
        } catch (error: Exception) {
            "Error: ${error.message}"
        }
    }
}

data class CreateAccountRequest(
    val userId: Long,
    val name: String,
    val accountNumber: String,
    val initialBalance: Double
)

data class TransferRequest(
    val sourceAccountNumber: String,
    val destinationAccountNumber: String,
    val amount: Double
)
