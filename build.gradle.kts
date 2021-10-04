plugins {
    application
}

group = "net.cap5lut"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(16))
}

application {
    //mainModule.set("net.cap5lut.growbox")
    mainClass.set("net.cap5lut.growbox.Application")
}

dependencies {
    val jacksonVersion = "2.12.5"
    val log4jVersion = "2.14.1"
    val junitVersion = "5.8.1"

    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner:$jacksonVersion")
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("io.javalin:javalin:4.1.0")
    implementation("io.javalin:javalin-openapi:4.1.0")
    implementation("net.cap5lut:cap5lut-database:1.0.0-SNAPSHOT")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.postgresql:postgresql:42.2.23")
    implementation("org.webjars:chartjs:3.5.1")
    implementation("org.webjars.npm:moment:2.29.1")
    implementation("org.webjars.npm:vue:3.2.10")

    runtimeOnly("org.apache.logging.log4j:log4j-core:$log4jVersion")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j18-impl:$log4jVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.getByName<JavaExec>("run") {
    environment["net.cap5lut.growbox.database.url"] = "jdbc:postgresql://localhost:5433/growbox?user=growbox&password=growbox"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}