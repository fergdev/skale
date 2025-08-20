package io.fergdev.skale.example

/**
 * JS platform.
 */
class JsPlatform : Platform {
    override val name: String
        get() = "JsLand"
}

/**
 * Actual for JS platform.
 */
actual fun getPlatform(): Platform = JsPlatform()
