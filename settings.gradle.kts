plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "commerce"
include("commerce-common", "commerce-service", "commerce-interfaces", "commerce-batch", "commerce-infrastructure")
