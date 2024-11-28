import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
jar.enabled = true
jar.duplicatesStrategy = DuplicatesStrategy.EXCLUDE
val bootJar: BootJar by tasks
bootJar.enabled = false

plugins {
    // jpa
    id("org.flywaydb.flyway") version "7.2.0"
}

dependencies {
    implementation(project(":commerce-common"))

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.data:spring-data-envers")

    // querydsl
    val queryDslVersion = "5.1.0"
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

flyway {
    url = "jdbc:mysql://localhost:3306/commerce"
    user = "root"
    password = ""
}

// querydsl
kotlin.sourceSets.main {
    kotlin.srcDir(layout.buildDirectory.dir("/generated/source/kapt/main").get().asFile)
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
