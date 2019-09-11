import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-RC")
    implementation("org.jsoup:jsoup:1.10.3")

    //Firebase
    implementation("com.google.api-client:google-api-client:1.30.2")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.30.1")

    implementation("com.google.firebase:firebase-admin:6.10.0")
    implementation("com.google.apis:google-api-services-sheets:v4-rev581-1.25.0")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}