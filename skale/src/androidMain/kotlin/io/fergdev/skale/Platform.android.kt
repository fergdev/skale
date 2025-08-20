package io.fergdev.skale

import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

@Composable
internal actual fun ProvidePlatformLocals(content: @Composable () -> Unit) {
    content()
}

private const val NameHighContrastText = "high_text_contrast_enabled"
private const val HighContrastTextDisabled = 0
private const val HighContrastTextEnabled = 1
private fun Context.isHighContrastTextContrastEnabled(): Boolean =
    Settings.Secure.getInt(
        contentResolver,
        NameHighContrastText,
        HighContrastTextDisabled
    ) == HighContrastTextEnabled

@Composable
private fun FontWeightAdjustments() {
    val configuration = LocalConfiguration.current
    val fontWeightAdjustments by remember {
        val minVersion = Build.VERSION_CODES.S
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= minVersion) {
                "${configuration.fontWeightAdjustment}"
            } else {
                "Required API '$minVersion'"
            }
        )
    }
    InfoText("Font Weight adjustments = $fontWeightAdjustments")
}

internal actual fun LazyListScope.platformItems() {
    item {
        FontWeightAdjustments()
    }
    item {
        InfoText("High contrast text enabled = ${LocalContext.current.isHighContrastTextContrastEnabled()}")
    }
}
