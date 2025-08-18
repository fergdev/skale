package com.fergdev.skale.example

class JsPlatform : Platform {
    override val name: String
        get() = "JsLand"
}

actual fun getPlatform() : Platform = JsPlatform()
