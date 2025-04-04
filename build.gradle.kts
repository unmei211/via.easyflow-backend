plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "2.1.10"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "via.easyflow"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()

    mavenLocal()
}

extra["springModulithVersion"] = "1.1.9"

dependencies {
    // library import
//    implementation("via.easyflow:library:0.0.1);
    implementation("via.easyflow:library")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
//    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
//    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.postgresql:r2dbc-postgresql")
    implementation("org.postgresql:postgresql")

    implementation("org.springframework.boot:spring-boot-starter-security")
//    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.modulith:spring-modulith-events-api")
    implementation("org.springframework.modulith:spring-modulith-starter-core")
//    implementation("org.springframework.modulith:spring-modulith-starter-jdbc")
    implementation("io.swagger.core.v3:swagger-annotations:2.1.11")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.15")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.resilience4j:resilience4j-ratelimiter:2.2.0")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")
    implementation("org.springframework:spring-context-support")
    implementation("org.hibernate.validator:hibernate-validator")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
    runtimeOnly("org.springframework.modulith:spring-modulith-observability")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-core:4.11.0")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = true
    }
}
