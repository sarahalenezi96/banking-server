package com.bank.banking_server.users

import com.bank.banking_server.exceptions.InvalidPasswordException
import com.bank.banking_server.exceptions.UnderageUserException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period

@Service
class UserService(
    private val userRepo: UserRepository,
    private val kycRepo: KYCRepository
) {
    fun register(username: String, password: String) {
        if (password.length < 6) {
            throw InvalidPasswordException("Password must be at least 6 characters long")
        }
        if (userRepo.findByUsername(username) != null) {
            throw Exception("User already exists")
        }
        userRepo.save(User(username = username, password = password))
    }

    fun getKYC(userId: Long): KYC {
        return kycRepo.findById(userId).orElseThrow { Exception("KYC not found") }
    }

    fun createOrUpdateKYC(userId: Long, firstName: String, lastName: String, birthDate: LocalDate, salary: Double): KYC {
        val age = Period.between(birthDate, LocalDate.now()).years
        if (age < 18) {
            println("User age is: $age")  // making sure its calculated right
            throw UnderageUserException("User must be at least 18 years old")
        }


        val kyc = KYC(userId, firstName, lastName, birthDate, salary)
        return kycRepo.save(kyc)
    }

}
