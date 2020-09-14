import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
		maven(url = "http://repo.spring.io/plugins-release")
		maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
	}
	dependencies {
		classpath("org.liquibase:liquibase-gradle-plugin:2.0.1")
	}
}
plugins {
	id("org.springframework.boot") version "2.3.3.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	id("org.liquibase.gradle") version "2.0.1"

}

group = "com.ampmangu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}
liquibase {
	activities.register("main") {
		this.arguments = mapOf(

				"driver" to "org.mariadb.jdbc.Driver",
				"url" to "jdbc:mariadb://localhost:3306/7degrees",
				"username" to "root",
				"password" to "",
				"changeLogFile" to "src/main/resources/db/changelog/master.xml",
				"referenceUrl" to "hibernate:spring:com.ampmangu.degrees.domain?dialect=org.hibernate.dialect.H2Dialect&amp;hibernate.physical_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy&amp;hibernate.implicit_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
				"defaultSchemaName" to "",
				"logLevel" to "debug",
				"classpath" to "$buildDir/classes/java/main"
		)
	}


}
dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework:spring-beans")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.liquibase:liquibase-core")
	implementation("org.mariadb.jdbc:mariadb-java-client:2.3.0")
	implementation("com.zaxxer:HikariCP:3.2.0")
	testImplementation("com.h2database:h2:1.4.199")

	liquibaseRuntime("org.liquibase:liquibase-core:3.6.3")
	liquibaseRuntime("org.liquibase.ext:liquibase-hibernate5:3.6")
	liquibaseRuntime("org.mariadb.jdbc:mariadb-java-client:2.3.0")
	liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:2.0.1")

	// HIBERNATE
	annotationProcessor("org.hibernate:hibernate-jpamodelgen")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5")
	implementation("org.hibernate:hibernate-core")
	implementation("org.hibernate:hibernate-jcache")
	implementation("org.hibernate:hibernate-entitymanager")
	implementation("org.hibernate:hibernate-envers")
	implementation("org.hibernate.validator:hibernate-validator")
	implementation("org.hibernate:hibernate-ehcache")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
