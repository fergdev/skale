package com.fergdev.skale.example

import android.os.Build

/**
 * Android platform impl.
 */
class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

/**
 * Actual android platform.
 */
actual fun getPlatform(): Platform = AndroidPlatform()
