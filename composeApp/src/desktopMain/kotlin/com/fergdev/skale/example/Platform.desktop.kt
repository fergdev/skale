package com.fergdev.skale.example

/**
 * Desktop platform impl.
 */
class DesktopPlatform : Platform {
    override val name: String
        get() = "Desktop"
}

/**
 * Actual desktop platform.
 */
actual fun getPlatform(): Platform = DesktopPlatform()
