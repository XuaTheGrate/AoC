plugins {
    java
    kotlin("jvm") version "1.4.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor", "ktor-client-cio-jvm", "1.4.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=io.ktor.util.KtorExperimentalAPI"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}