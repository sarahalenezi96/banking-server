package com.bank.banking_server.transactions

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Transaction(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val accountNumber: String,
    val type: String,
    val amount: Double,
    val timestamp: LocalDateTime = LocalDateTime.now()
)