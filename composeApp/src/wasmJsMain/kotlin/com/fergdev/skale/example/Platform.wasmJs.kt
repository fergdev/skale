package com.fergdev.skale.example

class WasmJsPlatform : Platform {
    override val name: String
        get() = "WasmJsLand"
}

actual fun getPlatform() : Platform = WasmJsPlatform()
