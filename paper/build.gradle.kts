plugins {
    id("java")
    `maven-publish`
}

version = "2.0.0"
group = "bet.astral"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Self
    compileOnly(project(":"))
    // Adventure
    // Paper
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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
            artifactId = "messenger-paper"
            from(components["java"])
        }
    }
}


