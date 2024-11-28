import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
jar.enabled = true
jar.duplicatesStrategy = DuplicatesStrategy.EXCLUDE
val bootJar: BootJar by tasks
bootJar.enabled = false

plugins {
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // aws
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
    implementation("ca.pjer:logback-awslogs-appender:1.6.0")
}
