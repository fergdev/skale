@file:Suppress("Filename")

package io.fergdev.skale.example

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

/**
 * Main entry point for the application.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(content = {
        App()
    })
}
