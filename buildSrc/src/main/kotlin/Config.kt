@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "MissingPackageDeclaration",
    "UndocumentedPublicClass",
    "UndocumentedPublicProperty",
    "MaxLineLength"
)
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

public object Config {
    const val group = "com.fergdev"
    const val artifact = "skale"
    const val artifactId = "$group.$artifact"

    const val name = "Skale"

    const val majorRelease = 0
    const val minorRelease = 1
    const val patch = 0
    const val postfix = "-alpha06"
    const val versionCode = 1

    const val majorVersionName = "$majorRelease.$minorRelease.$patch"
    const val versionName = "$majorVersionName$postfix"

    const val description =
        """A Compose Multiplatform library to help with accessiblity testing and development."""

    const val url = "https://github.com/fergdev/skale"

    val jvmTarget = JvmTarget.JVM_11
    val javaVersion = JavaVersion.VERSION_11

    object Android {
        const val compileSdk = 36
        const val targetSdk = compileSdk
        const val minSdk = 21
        const val isMinifyEnabledRelease = false
        const val isMinifyEnabledDebug = false
    }

    val optIns = listOf(
        "kotlin.RequiresOptIn",
        "kotlin.experimental.ExperimentalTypeInference",
        "kotlin.uuid.ExperimentalUuidApi",
        "kotlin.time.ExperimentalTime",
        "kotlin.contracts.ExperimentalContracts",
    )
    val compilerArgs = listOf(
        "-Xexpect-actual-classes",
        "-Xconsistent-data-class-copy-visibility",
        "-Xwarning-level=NOTHING_TO_INLINE:disabled",
        "-Xwarning-level=UNUSED_ANONYMOUS_PARAMETER:disabled",
    )
    val jvmCompilerArgs = buildList {
        add("-Xjvm-default=all") // enable all jvm optimizations
        add("-Xcontext-parameters")
        add("-Xstring-concat=inline")
        add("-Xlambdas=indy")
        add("-Xjdk-release=${jvmTarget.target}")
    }

    val wasmCompilerArgs = buildList {
        add("-Xwasm-use-new-exception-proposal")
        add("-Xwasm-debugger-custom-formatters")
    }
    object Detekt {
        const val configFile = "detekt.yml"
        val includedFiles = listOf("**/*.kt", "**/*.kts")
        val excludedFiles = listOf("**/resources/**", "**/build/**", "**/.idea/**", "**/**Test/**")
    }
}
