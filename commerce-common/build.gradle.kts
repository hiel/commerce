import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
jar.enabled = true
jar.duplicatesStrategy = DuplicatesStrategy.EXCLUDE
val bootJar: BootJar by tasks
bootJar.enabled = false

plugins {
    kotlin("kapt")
}

dependencies {
    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
}
