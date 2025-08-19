package com.fergdev.skale.example

/**
 * Greeting class.
 */
class Greeting {
    private val platform = getPlatform()

    /**
     * Generate platform greeting.
     */
    fun greet(): String = "Hello, ${platform.name}!"
}
