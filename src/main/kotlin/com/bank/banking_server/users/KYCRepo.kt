package com.bank.banking_server.users
import org.springframework.data.jpa.repository.JpaRepository

interface KYCRepository : JpaRepository<KYC, Long>