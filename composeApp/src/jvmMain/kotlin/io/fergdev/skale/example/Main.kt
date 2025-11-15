package io.fergdev.skale.example

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * Main function.
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Skale"
    ) {
        App()
    }
}
