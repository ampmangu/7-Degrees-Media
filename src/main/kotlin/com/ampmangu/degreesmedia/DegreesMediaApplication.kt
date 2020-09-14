package com.ampmangu.degreesmedia

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.core.env.ConfigurableEnvironment

@SpringBootApplication
@EnableConfigurationProperties(LiquibaseProperties::class)
class DegreesMediaApplication

val log: Logger = LoggerFactory.getLogger(DegreesMediaApplication::class.java)
fun main(args: Array<String>) {
	val app = SpringApplication(DegreesMediaApplication::class.java)
	val env = app.run(*args).environment
	logApplicationStartup(env)
}

fun logApplicationStartup(env: ConfigurableEnvironment) {
	log.info("Hello there")

}
