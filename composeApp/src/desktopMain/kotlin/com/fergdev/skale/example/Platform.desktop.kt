package com.fergdev.skale.example

class DesktopPlatform : Platform {
    override val name: String
        get() = "Desktop"
}

actual fun getPlatform() : Platform = DesktopPlatform()