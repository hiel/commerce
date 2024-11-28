import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
jar.enabled = false
val bootJar: BootJar by tasks
bootJar.enabled = true

plugins {
}

dependencies {
    implementation(project(":commerce-common"))

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    "developmentOnly"("org.springframework.boot:spring-boot-devtools")
}
