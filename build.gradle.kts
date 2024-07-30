plugins {
    kotlin("jvm") version "2.0.0"
    id("application")
}

group = "org.deimoscm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(files("/home/matthew/src/Projects/MarodiGameLib/app/build/libs/MarodiGameLibrary-1.0-SNAPSHOT.jar"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass = "org.deimoscm.MainKt"
}