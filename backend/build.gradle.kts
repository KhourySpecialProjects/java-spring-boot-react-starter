import net.ltgt.gradle.errorprone.errorprone

plugins {
	java
	id("org.springframework.boot") version "4.1.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.diffplug.spotless") version "8.10.4"
	id("net.ltgt.errorprone") version "5.1.1"
	id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-flyway")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.flywaydb:flyway-database-postgresql")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:3.1.1")
	runtimeOnly("org.postgresql:postgresql")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	errorprone("com.google.errorprone:error_prone_core:2.50.0")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile>().configureEach {
	options.errorprone.disableWarningsInGeneratedCode = true
}

tasks.withType<Test> {
	useJUnitPlatform()
}

spotless {
	java {
		googleJavaFormat()
	}
}

// `mise run contract` boots the app with the openapi profile, which needs no
// database, and saves the spec to backend/openapi.json.
openApi {
	apiDocsUrl = "http://127.0.0.1:3999/api/v1/openapi.json"
	outputDir = layout.projectDirectory
	outputFileName = "openapi.json"
	customBootRun {
		args = listOf("--spring.profiles.active=openapi")
	}
}
