pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://jitpack.io")
        maven("https://maven.fabricmc.net")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.architectury.dev/")
        flatDir {
                dirs=setOf(file("../../libs"))
        }
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.replaymod.preprocess" -> {
                    useModule("com.github.replaymod:preprocessor:${requested.version}")
                }
            }
        }
    }
}

rootProject.buildFileName = "root.gradle.kts"

listOf(
    "1.8.9",
    "1.12.2"
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../../build.gradle"
    }

}
