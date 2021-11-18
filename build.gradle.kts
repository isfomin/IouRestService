import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
	kotlin("kapt") version "1.5.10"
}

group = "com.codereview"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

val springVersion = "2.5.6"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.liquibase:liquibase-core:4.6.1")
	implementation("org.mapstruct:mapstruct:1.4.2.Final")
	implementation("junit:junit:4.13.2")
	kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")
	runtimeOnly("org.postgresql:postgresql:42.3.1")
	runtimeOnly("com.h2database:h2:1.4.200")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.6")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
