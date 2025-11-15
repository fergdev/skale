@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
    id(libs.plugins.androidKotlinMultiplatformLibrary.get().pluginId)
    id(libs.plugins.androidLint.get().pluginId)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.maven.publish)
    id("signing")
}

group = Config.artifactId
version = Config.versionName

kotlin {
    explicitApi()
    withSourcesJar(true)
    compilerOptions {
        extraWarnings.set(true)
        freeCompilerArgs.addAll(Config.compilerArgs)
        optIn.addAll(Config.optIns)
        progressiveMode.set(true)
    }
    androidLibrary {
        namespace = Config.artifactId
        compileSdk = Config.Android.compileSdk
        minSdk = Config.Android.minSdk

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        compilerOptions {
            jvmTarget.set(Config.jvmTarget)
            freeCompilerArgs.addAll(Config.jvmCompilerArgs)
        }
    }

    js {
        outputModuleName = Config.artifact
        browser()
        nodejs()
        binaries.library()
//        binaries.executable() // TODO: https://youtrack.jetbrains.com/projects/KT/issues/KT-80175/K-JS-Task-with-name-jsBrowserProductionWebpack-not-found-in-project
    }

    wasmJs {
        outputModuleName = Config.artifact
        browser()
        binaries.library()
//        binaries.executable() // TODO: https://youtrack.jetbrains.com/projects/KT/issues/KT-80175/K-JS-Task-with-name-jsBrowserProductionWebpack-not-found-in-project
        compilerOptions {
            freeCompilerArgs.addAll(Config.wasmCompilerArgs)
        }
    }
//    linuxX64()
//    linuxArm64()
//    mingwX64()
//    wasmWasi {
//        nodejs()
//    }

    jvm {
        compilerOptions {
            jvmTarget.set(Config.jvmTarget)
            freeCompilerArgs.addAll(Config.jvmCompilerArgs)
        }
    }

    val xcfName = "${Config.artifact}Kit"
    sequence {
        yield(iosX64())
        yield(iosArm64())
        yield(iosSimulatorArm64())
        yield(macosArm64())
        yield(macosX64())
//        yield(tvosX64())
//        yield(tvosArm64())
//        yield(tvosSimulatorArm64())
//        yield(watchosX64())
//        yield(watchosArm64())
//        yield(watchosDeviceArm64())
//        yield(watchosSimulatorArm64())
    }.forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true) // optional but convenient
    signAllPublications()
    coordinates(Config.artifactId, Config.artifact, Config.versionName)

    pom {
        name.set(Config.name)
        description.set(Config.description)
        inceptionYear = "2025"

        url.set(Config.url)
        licenses {
            license {
                name = Config.licenseName
                url = Config.licenseUrl
                distribution = Config.licenseDistribution
            }
        }
        scm {
            url.set(Config.url)
            connection.set("scm:git:git://github.com/fergdev/skale.git")
            developerConnection.set("scm:git:ssh://git@github.com/fergdev/skale.git")
        }
        developers {
            developer {
                id.set("fergdev")
                name.set("Fergus Hewson")
            }
        }
    }
}

signing {
    val key = providers.gradleProperty("signingInMemoryKey").orNull
    val pass = providers.gradleProperty("signingInMemoryKeyPassword").orNull
    if (!key.isNullOrBlank()) {
        useInMemoryPgpKeys(key, pass)
    }
    sign(publishing.publications)
}

tasks.withType<Sign>().configureEach {
    onlyIf { !project.version.toString().endsWith("SNAPSHOT") }
}
