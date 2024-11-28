import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
jar.enabled = false
val bootJar: BootJar by tasks
bootJar.enabled = true

plugins {
}

dependencies {
    implementation(project(":commerce-common"))
    implementation(project(":commerce-service"))
    implementation(project(":commerce-infrastructure"))

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    "developmentOnly"("org.springframework.boot:spring-boot-devtools")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.boot:spring-boot-starter-security")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
}
