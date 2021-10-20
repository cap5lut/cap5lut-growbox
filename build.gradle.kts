plugins {
    application
}

group = "net.cap5lut"
version = "1.0.0-SNAPSHOT"

application.mainClass.set("net.cap5lut.growbox.Application")

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner:2.13.0")
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("io.javalin:javalin:4.1.1")
    implementation("org.apache.logging.log4j:log4j-api:2.14.1")
    implementation("org.postgresql:postgresql:42.2.24")
    implementation("org.webjars.npm:vue:3.2.19")

    runtimeOnly("org.apache.logging.log4j:log4j-core:2.14.1")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}