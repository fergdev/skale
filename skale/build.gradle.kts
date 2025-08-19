@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
    id(libs.plugins.androidKotlinMultiplatformLibrary.get().pluginId)
    id(libs.plugins.androidLint.get().pluginId)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.maven.publish)
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

//        publishLibraryVariants("release")
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
    }

    wasmJs {
        outputModuleName = Config.artifact
        browser()
        binaries.library()
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

        androidMain {
            dependencies {
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

publishing {
    publications.withType<MavenPublication>().configureEach {
        pom {
            name.set(Config.name)
            description.set(Config.description)
            url.set(Config.url)
            licenses {
                license {
                    name.set("Apache-2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0")
                }
            }
            scm { url.set(Config.url) }
        }
    }
    repositories {
        mavenLocal()
        // GitHub Packages
        // maven { name="GitHub"; url=uri("https://maven.pkg.github.com/yourorg/yourrepo"); credentials{...} }
        // Maven Central (OSSRH) endpoints...
    }
}
