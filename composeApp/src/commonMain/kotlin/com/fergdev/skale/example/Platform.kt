package com.fergdev.skale.example

/**
 * Platform interface.
 */
interface Platform {

    /**
     * Name of the platform.
     */
    val name: String
}

/**
 * Expect for platform.
 */
expect fun getPlatform(): Platform
