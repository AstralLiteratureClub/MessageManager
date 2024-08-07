plugins {
    id("java")
    id("maven-publish")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")

}

dependencies {
    compileOnly(project(":"))
    compileOnly(project(":Cloud"))
    compileOnly(project(":Cloud-Bukkit"))
    compileOnly(project(":Paper"))

    compileOnly("org.incendo:cloud-core:2.0.0-rc.1")
    compileOnly("org.incendo:cloud-minecraft-extras:2.0.0-beta.8")
    compileOnly("org.incendo:cloud-translations-core:1.0.0-beta.2")
    compileOnly("org.incendo:cloud-translations-bukkit:1.0.0-beta.2")

    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")

}

tasks.test {
    useJUnitPlatform()
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "messenger-cloud-paper"
            from(components["java"])
        }
    }
}

