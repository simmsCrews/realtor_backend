import org.gradle.api.tasks.Copy

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// submodule yml
tasks.register<Copy>("copySecret") {
    from("$rootDir/realtor_backend-config")
    include("application-admin.yml")
    into("$rootDir/system-admin/src/main/resources")
}

tasks.named("processResources") {
    dependsOn("copySecret")
}