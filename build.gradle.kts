import com.commercehub.gradle.plugin.avro.GenerateAvroJavaTask

plugins {
    id("org.springframework.boot") version "2.3.12.RELEASE"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    kotlin("plugin.jpa") version "1.5.31"
    id("org.flywaydb.flyway") version "6.5.7"
}

group = "com.ss.challenge"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("http://packages.confluent.io/maven/")
        isAllowInsecureProtocol = true
    }
}

buildscript {

    dependencies {
        classpath("com.commercehub.gradle.plugin:gradle-avro-plugin:0.20.0")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.3.12.RELEASE")
    }
}

apply(plugin = "com.commercehub.gradle.plugin.avro")
apply(plugin = "com.commercehub.gradle.plugin.avro-base")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:2.2.5.RELEASE")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:2.10.5")
    implementation("com.github.vladimir-bukhtoyarov:bucket4j-core:6.0.2")
    implementation("io.confluent:kafka-avro-serializer:5.3.0")
    implementation("org.apache.avro:avro:1.9.0")
    implementation("org.scala-lang:scala-library:2.12.11")
    runtimeOnly("mysql:mysql-connector-java:8.0.17")
    implementation("org.flywaydb:flyway-core:6.5.7")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.ninja-squad:springmockk:2.0.1")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("com.h2database:h2:1.3.148")
    testImplementation("org.springframework:spring-test:5.2.13.RELEASE")
    testImplementation("org.mock-server:mockserver-netty:5.11.2")
    testImplementation("org.awaitility:awaitility-kotlin")
}

tasks.withType<Test> {
    failFast = true
    useJUnitPlatform()
}

val generateAvro = tasks.register<com.commercehub.gradle.plugin.avro.GenerateAvroJavaTask>(
    "generateAvro"
) {
    source("src/main/avro")
    setOutputDir(file("src/generated/java/avro"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {

    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()

    dependsOn(tasks.getByName("generateAvro"))

    kotlinOptions {
        jvmTarget = "1.8"
        apiVersion = "1.4"
        languageVersion = "1.4"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}