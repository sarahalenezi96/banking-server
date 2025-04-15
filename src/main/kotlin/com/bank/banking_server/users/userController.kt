package com.bank.banking_server.users

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterRequest): ResponseEntity<Void> {
        userService.register(body.username, body.password)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/kyc/{userId}")
    fun getKYC(@PathVariable userId: Long) = userService.getKYC(userId)

    @PostMapping("/kyc")
    fun createOrUpdateKYC(@RequestBody body: KYCRequest) =
        userService.createOrUpdateKYC(
            body.userId,
            body.firstName,
            body.lastName,
            body.dateOfBirth,
            body.salary
        )
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
