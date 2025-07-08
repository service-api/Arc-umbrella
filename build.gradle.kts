plugins {
    kotlin("jvm") version "2.1.21"
}

group = "arc.umbrella"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}