plugins {
    kotlin("jvm") version "1.9.10"
    id("fabric-loom") version "1.3-SNAPSHOT"
}

kotlin {
    explicitApi()
    jvmToolchain(17)
}

tasks {
    processResources {
        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
}

repositories {
    maven("https://maven.noxcrew.com/public")
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())

    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.kotlin)

    modImplementation(libs.sheeplib)
    include(libs.sheeplib)
}
