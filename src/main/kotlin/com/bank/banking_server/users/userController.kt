package com.bank.banking_server.users

import com.bank.banking_server.exceptions.InvalidPasswordException
import com.bank.banking_server.exceptions.UnderageUserException
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterRequest): Any {
        return try {
            userService.register(body.username, body.password)
            "Registration successful"
        } catch (error: InvalidPasswordException) {
            println("Invalid password: ${error.message}")
            "Error: ${error.message}"
        } catch (error: Exception) {
            println("Unexpected error: ${error.message}")
            "Error: ${error.message}"
        }
    }


    @GetMapping("/kyc/{userId}")
    fun getKYC(@PathVariable userId: Long): KYC? {
        return try {
            userService.getKYC(userId)
        } catch (error: Exception) {
            println(error.message)
            null
        }
    }

    @PostMapping("/kyc")
    fun createOrUpdateKYC(@RequestBody body: KYCRequest): String {
        return try {
            userService.createOrUpdateKYC(
                body.userId,
                body.firstName,
                body.lastName,
                body.dateOfBirth,
                body.salary
            )
            "KYC saved successfully"
        } catch (error: UnderageUserException) {
            "Error: ${error.message}"
        } catch (error: Exception) {
            "Unexpected error: ${error.message}"
        }
    }


}

data class RegisterRequest(
    val username: String,
    val password: String
)

data class KYCRequest(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val salary: Double
)
