plugins {
    id("java")
    id("maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()

    // Spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    implementation("bet.astral:more4j:1.0.0")
    // Self
    compileOnly(project(":"))
    // Adventure
    // Paper
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT") // The Spigot API with no shadowing. Requires the OSS repo.
    compileOnly("org.projectlombok:lombok:1.18.26")
    compileOnly("org.apiguardian:apiguardian-api:1.1.2")

    implementation("org.slf4j:slf4j-api:2.0.13")

    annotationProcessor("org.projectlombok:lombok:1.18.22")

    // message component api
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
tasks.test {
    useJUnitPlatform()
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "messenger-bukkit"
            from(components["java"])
        }
    }
}

