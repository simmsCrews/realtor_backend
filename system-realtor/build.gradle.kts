import org.gradle.api.tasks.Copy

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// submodule yml
tasks.register<Copy>("copySecret") {
    from("$rootDir/realtor_backend-config")
    include("application-realtor.yml")
    into("$rootDir/system-realtor/src/main/resources")
}

tasks.named("processResources") {
    dependsOn("copySecret")
}
