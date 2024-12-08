import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.github.ben-manes.versions") version "0.48.0"
    application
    id("io.freefair.lombok") version "8.6"
}

application {
    mainClass.set("exercise.App")
}

group = "exercise"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.2.0")

    // BEGIN
    implementation("gg.jte:jte:3.1.12")
    implementation("io.javalin:javalin-rendering:6.1.6")
    // END

    implementation("io.javalin:javalin-bundle:6.2.0")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("net.datafaker:datafaker:2.3.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.26.3")
}

tasks.test {
    useJUnitPlatform()
    // https://technology.lastminute.com/junit5-kotlin-and-gradle-dsl/
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        // showStackTraces = true
        // showCauses = true
        showStandardStreams = true
    }
}
