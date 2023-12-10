
val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val kmongoVersion: String by project

plugins {
    kotlin("jvm") version "1.9.21"
    id("io.ktor.plugin") version "2.3.7"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
}

group = "com.stancz_levente"
version = "1.0.0"

application {
    mainClass.set("com.stancz_levente.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    implementation("org.litote.kmongo:kmongo:$kmongoVersion")
    implementation ("io.ktor:ktor-client-core:$ktorVersion")
    implementation ("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation ("org.mindrot:jbcrypt:0.4")
}
