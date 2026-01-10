import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.9.25" apply false
	kotlin("plugin.spring") version "1.9.25" apply false
	id("org.springframework.boot") version "3.5.9" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
	group = "com.myexam"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "java")

	extensions.configure<JavaPluginExtension> {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(21))
		}
	}

	// Kotlin 컴파일 옵션(필요시 조정)
	tasks.withType<KotlinCompile>().configureEach {
		compilerOptions {
			jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
			freeCompilerArgs.addAll(
				"-Xjsr305=strict"
			)
		}
	}

	tasks.withType<Test>().configureEach {
		useJUnitPlatform()
	}

	dependencies {
		add("implementation", "org.jetbrains.kotlin:kotlin-reflect")
		add("testImplementation", "org.jetbrains.kotlin:kotlin-test-junit5")
		add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")
	}
}

