@file:Suppress("Filename")

package com.fergdev.skale.example

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

/**
 * Main entry point for the app.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(content = {
        App()
    })
}
