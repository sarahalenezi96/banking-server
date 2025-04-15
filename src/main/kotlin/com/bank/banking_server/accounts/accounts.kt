package com.bank.banking_server.accounts

import com.bank.banking_server.users.User
import jakarta.persistence.*


@Entity
data class Account(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val accountNumber: String,
    val name: String,
    var balance: Double,
    var isClosed: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // Foreign key
    val user: User
)
