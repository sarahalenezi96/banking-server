package com.bank.banking_server.accounts

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByAccountNumber(accountNumber: String): Account?
    fun findAllByUserId(userId: Long): List<Account>
}
