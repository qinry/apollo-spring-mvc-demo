import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
    war
    `maven-publish`
}

repositories {
    mavenCentral()
}

group = "demo"
version = "1.0-SNAPSHOT"
description = "apollo-spring-mvc-demo"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    testCompileOnly {
        extendsFrom(configurations.testAnnotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:1.5.8.RELEASE"))
    implementation(platform("org.springframework.data:spring-data-releasetrain:Fowler-SR2"))
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:Edgware.SR6"))
    implementation("org.springframework.cloud:spring-cloud-context")
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.joda:joda-money:1.0.1")
    implementation("com.google.guava:guava")
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-webmvc")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-aop")
    implementation("org.springframework:spring-aspects")
    implementation("org.aspectj:aspectjweaver")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework:spring-jdbc")
    implementation("com.ctrip.framework.apollo:apollo-client:2.0.1")
    implementation("org.mybatis:mybatis:3.4.0")
    implementation("org.mybatis:mybatis-spring:1.3.0")
    implementation("org.mybatis.generator:mybatis-generator-core:1.4.1")
    implementation("com.github.pagehelper:pagehelper:5.3.1")
    implementation("com.alibaba:druid:1.2.11")
    implementation("mysql:mysql-connector-java")
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-core")
    implementation("ch.qos.logback:logback-classic")
    testImplementation("junit:junit")
    annotationProcessor("org.projectlombok:lombok:1.16.18")
    testAnnotationProcessor("org.projectlombok:lombok:1.16.18")
    providedCompile("javax.servlet:javax.servlet-api:3.1.0")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
val buildProfile: String? by project

apply(from = "profile-${buildProfile ?: "default"}.gradle.kts")

tasks {
    processResources {
        filter(ReplaceTokens::class,
                "tokens" to mapOf(
                        "apollo.env" to project.extra["apollo.env"],
                        "apollo.cluster" to project.extra["apollo.cluster"]))
    }
}
