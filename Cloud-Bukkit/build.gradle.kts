plugins {
    id("java")
    id("maven-publish")
}

group = "bet.astral"
version = "2.0.4"

repositories {
    mavenLocal()
    mavenCentral()

    // Spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    compileOnly(project(":"))
    compileOnly(project(":Cloud"))
    compileOnly(project(":Bukkit"))
    compileOnly("org.jetbrains:annotations:24.0.0")
    implementation("org.slf4j:slf4j-api:2.0.13")

    compileOnly("org.incendo:cloud-core:2.0.0-rc.1")
    compileOnly("org.incendo:cloud-minecraft-extras:2.0.0-beta.8")
    compileOnly("org.incendo:cloud-translations-core:1.0.0-beta.2")
    compileOnly("org.incendo:cloud-translations-bukkit:1.0.0-beta.2")

    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT") // The Spigot API with no shadowing. Requires the OSS repo.

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "messenger-cloud-bukkit"
            from(components["java"])
        }
    }
}

