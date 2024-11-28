import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
jar.enabled = true
val bootJar: BootJar by tasks
bootJar.enabled = false

plugins {
    val kotlinVersion = "1.9.25"

    // kotlin
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion apply false
    kotlin("plugin.allopen") version kotlinVersion apply false
    kotlin("plugin.noarg") version kotlinVersion apply false

    // spring
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6" apply false
    kotlin("plugin.spring") version kotlinVersion apply false
}

subprojects {
    // kotlin
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "org.jetbrains.kotlin.plugin.noarg")
    apply(plugin = "org.jetbrains.kotlin.kapt")

    // spring
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    dependencies {
        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // spring
        implementation("org.springframework.boot:spring-boot-starter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

allprojects {
    group = "com.hiel"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_21.toString()
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
