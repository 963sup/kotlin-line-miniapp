package dev.kotlinlineminiapp.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["dev.kotlinlineminiapp"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
