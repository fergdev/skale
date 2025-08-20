package io.fergdev.skale.example

/**
 * Wasm Platform impl.
 */
class WasmJsPlatform : Platform {
    override val name: String
        get() = "WasmJsLand"
}

/**
 * Expect wasm platform.
 */
actual fun getPlatform(): Platform = WasmJsPlatform()
