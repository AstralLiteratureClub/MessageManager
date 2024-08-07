plugins {
    id("java")
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Self
    compileOnly(project(":"))
    // Adventure
    compileOnly("net.kyori:adventure-api:4.14.0")
    compileOnly("net.kyori:adventure-text-serializer-legacy:4.14.0")
    // Cloud
    compileOnly("org.incendo:cloud-core:2.0.0-rc.1")
    compileOnly("org.incendo:cloud-minecraft-extras:2.0.0-beta.8")
    compileOnly("org.incendo:cloud-translations-core:1.0.0-beta.2")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "messenger-cloud"
            from(components["java"])
        }
    }
}
