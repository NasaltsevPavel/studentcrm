import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
	id("org.springframework.boot") version "3.0.4"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.7.22"
}

group = "de.novatec.pna"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.flywaydb:flyway-core")
	compileOnly("org.projectlombok:lombok:1.18.26")
	implementation("org.apache.commons:commons-io:1.3.2")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.ninja-squad:springmockk:4.0.0")
	testImplementation("io.mockk:mockk:1.9.3")
	testImplementation("org.mockito:mockito-core")
	implementation("org.flywaydb.flyway-test-extensions:flyway-spring4-test:7.0.0")
	implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")

	// Spring security
//	implementation("org.springframework.boot:spring-boot-starter-security:3.0.1")

	// oAuth2
//	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:3.0.1")
//	implementation("org.springframework.security:spring-security-oauth2-jose:5.7.3")
//	implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.0.4")

	// keycloak
//	implementation("org.keycloak:keycloak-core:21.0.1")
//	implementation("org.keycloak:keycloak-services:21.0.1")
//	implementation("org.keycloak:keycloak-spring-boot-starter:21.0.1")
//	implementation("org.keycloak:keycloak-admin-client:20.0.1")
//	implementation("org.keycloak:keycloak-spring-security-adapter")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
