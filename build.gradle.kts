plugins {
    application
    id("io.github.zebalu.teavm-gradle-plugin") version "1.0.0"
}

group = "net.cap5lut"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.5")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.5")
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner:2.12.5")
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("io.javalin:javalin:4.0.0")
    implementation("net.cap5lut:cap5lut-database:1.0.0-SNAPSHOT")
    implementation("org.apache.logging.log4j:log4j-api:2.14.1")
    implementation("org.postgresql:postgresql:42.2.23")
    implementation("org.teavm:teavm-classlib:0.6.1")
    implementation("org.teavm:teavm-jso:0.6.1")
    implementation("org.teavm:teavm-jso-apis:0.6.1")
    implementation("org.teavm.flavour:teavm-flavour-json:0.2.1")
    implementation("org.webjars:chartjs:3.5.1")
    implementation("org.webjars.npm:vue:3.2.10")
    implementation("org.webjars.npm:moment:2.29.1")

    runtimeOnly("org.apache.logging.log4j:log4j-core:2.14.1")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.7.1")
    testImplementation("org.mockito", "mockito-core", "3.11.2")

    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.7.1")
}

application {
    mainClass.set("net.cap5lut.growbox.Application")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<JavaExec>("run") {
    jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")

    systemProperties["net.cap5lut.growbox.hostname"] = "localhost"
    systemProperties["net.cap5lut.growbox.port"] = "8080"
    systemProperties["net.cap5lut.database"] = "jdbc:postgresql://localhost:5433/growbox?user=growbox&password=growbox"
}