package com.ampmangu.degreesmedia.config

import liquibase.integration.spring.SpringLiquibase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.task.TaskExecutor
import javax.sql.DataSource

class LiquibaseConfiguration {
	val logger: Logger = LoggerFactory.getLogger(LiquibaseConfiguration::class.java)

	//TODO Remove later on
	@Bean
	fun liquibase(@Qualifier("taskExecutor") taskExecutor: TaskExecutor,
				  datasource: DataSource, liquibaseProperties: LiquibaseProperties): SpringLiquibase {
		logger.info("Configuring liquibase")
		val liquibase: SpringLiquibase = SpringLiquibase()
		liquibase.dataSource = datasource
		liquibase.changeLog = "classpath:/db/changelog/master.xml"
		liquibase.contexts = liquibaseProperties.contexts
		liquibase.defaultSchema = liquibase.defaultSchema
		liquibase.isDropFirst = liquibaseProperties.isDropFirst
		liquibase.setChangeLogParameters(liquibaseProperties.parameters)
		liquibase.setShouldRun(liquibaseProperties.isEnabled)
		logger.info("Liquibase configured")
		return liquibase
	}
}