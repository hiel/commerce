import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
jar.enabled = false
val bootJar: BootJar by tasks
bootJar.enabled = true

plugins {
}

dependencies {
    implementation(project(":commerce-common"))
}
