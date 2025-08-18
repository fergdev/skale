@file:Suppress("Filename")

package com.fergdev.skale.example

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(
        canvasElementId = "ComposeTarget",
        title = "Skale example"
    ) {
        App()
    }
}
