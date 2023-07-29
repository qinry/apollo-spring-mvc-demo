plugins {
    java
    war
    `maven-publish`
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
    implementation("com.fasterxml.jackson.core:jackson-core:2.13.5")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.5")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.13.5")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda:2.13.5")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.5")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.5")
    implementation("org.joda:joda-money:1.0.1")
    implementation("com.google.guava:guava:20.0")
    implementation("org.springframework:spring-web:4.3.30.RELEASE")
    implementation("org.springframework:spring-webmvc:4.3.30.RELEASE")
    implementation("org.springframework:spring-context:4.3.30.RELEASE")
    implementation("org.springframework:spring-aop:4.3.30.RELEASE")
    implementation("org.springframework:spring-aspects:4.3.30.RELEASE")
    implementation("org.aspectj:aspectjweaver:1.9.7")
    implementation("org.springframework:spring-tx:4.3.30.RELEASE")
    implementation("org.springframework:spring-jdbc:4.3.30.RELEASE")
    implementation("com.ctrip.framework.apollo:apollo-client:2.0.1")
    implementation("org.mybatis:mybatis:3.4.0")
    implementation("org.mybatis:mybatis-spring:1.3.0")
    implementation("org.mybatis.generator:mybatis-generator-core:1.4.1")
    implementation("com.github.pagehelper:pagehelper:5.3.1")
    implementation("com.alibaba:druid:1.2.11")
    implementation("mysql:mysql-connector-java:5.1.47")
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-core:1.2.12")
    implementation("ch.qos.logback:logback-classic:1.2.12")
    testImplementation("junit:junit:4.13.2")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")
    providedCompile("javax.servlet:javax.servlet-api:3.1.0")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}