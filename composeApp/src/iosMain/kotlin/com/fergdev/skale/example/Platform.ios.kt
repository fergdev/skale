package com.fergdev.skale.example

import platform.UIKit.UIDevice

/**
 * IOS platform impl.
 */
class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

/**
 * Actual for IOS platform.
 */
actual fun getPlatform(): Platform = IOSPlatform()
