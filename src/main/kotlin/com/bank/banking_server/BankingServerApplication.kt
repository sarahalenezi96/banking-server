package com.bank.banking_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankingServerApplication

fun main(args: Array<String>) {
	runApplication<BankingServerApplication>(*args)
}
