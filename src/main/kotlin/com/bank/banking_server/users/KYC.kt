package com.bank.banking_server.users

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
data class KYC(
    @Id
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val salary: Double
)
