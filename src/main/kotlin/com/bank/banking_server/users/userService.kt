package com.bank.banking_server.users

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UserService(
    private val userRepo: UserRepository,
    private val kycRepo: KYCRepository
) {
    fun register(username: String, password: String) {
        if (userRepo.findByUsername(username) != null) throw Exception("User already exists")
        userRepo.save(User(username = username, password = password))
    }

    fun getKYC(userId: Long): KYC =
        kycRepo.findById(userId).orElseThrow { Exception("KYC not found") }

    fun createOrUpdateKYC(userId: Long, firstName: String, lastName: String, dob: LocalDate, salary: Double): KYC {
        val kyc = KYC(userId, firstName, lastName, dob, salary)
        return kycRepo.save(kyc)
    }
}
