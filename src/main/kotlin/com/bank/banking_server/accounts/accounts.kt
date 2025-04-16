package com.bank.banking_server.accounts

import com.bank.banking_server.users.User
import jakarta.persistence.*

@Entity
@Table(name = "accounts")
data class Account(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val accountNumber: String,
    val name: String,
    var balance: Double,
    var isActive: Boolean = true,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User
)
