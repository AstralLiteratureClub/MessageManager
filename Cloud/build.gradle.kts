plugins {
    id("java")
    `maven-publish`
}

version = "2.0.0"
group = "bet.astral"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Self
    compileOnly("bet.astral:messenger:$version")
    // Adventure
    compileOnly("net.kyori:adventure-api:4.14.0")
    compileOnly("net.kyori:adventure-text-serializer-legacy:4.14.0")
    // Cloud
    implementation("org.incendo:cloud-core:2.0.0-rc.1")
    implementation("org.incendo:cloud-minecraft-extras:2.0.0-beta.7")

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
