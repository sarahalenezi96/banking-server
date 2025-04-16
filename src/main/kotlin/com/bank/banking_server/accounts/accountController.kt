package com.bank.banking_server.accounts

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/accounts")
class AccountController(private val accountService: AccountService) {

    @GetMapping("/accounts")
    fun listAccounts(): Map<String, Any> =
        mapOf("accounts" to accountService.listAccounts())

    @PostMapping("/accounts")
    fun createAccount(@RequestBody body: CreateAccountRequest): Account =
        accountService.createAccount(body)

    @PostMapping("/accounts/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): ResponseEntity<Void> {
        accountService.closeAccount(accountNumber)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/accounts/transfer")
    fun transfer(@RequestBody body: TransferRequest): Map<String, Any> =
        mapOf("newBalance" to accountService.transfer(
            body.sourceAccountNumber,
            body.destinationAccountNumber,
            body.amount
        ))
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