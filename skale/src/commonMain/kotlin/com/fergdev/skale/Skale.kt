package com.fergdev.skale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.LayoutDirection.Ltr
import androidx.compose.ui.unit.LayoutDirection.Rtl
import androidx.compose.ui.unit.dp

private val LocalAccessibilityModifier = compositionLocalOf<AccessibilityModifier> {
    error("CompositionLocal LocalAccessibilityModifier not present")
}

private class AccessibilityModifier(
    val initialFontScale: Float,
    val initialDensity: Float,
    val initialLayoutDirection: LayoutDirection
) {
    val fontScale: MutableFloatState = mutableFloatStateOf(initialFontScale)
    val density: MutableFloatState = mutableFloatStateOf(initialDensity)
    val layoutDirection: MutableState<LayoutDirection> = mutableStateOf(initialLayoutDirection)
}

private var global: AccessibilityModifier? = null
private fun accessibilityModifier(
    fontScale: Float,
    density: Float,
    layoutDirection: LayoutDirection
): AccessibilityModifier {
    val out = global ?: AccessibilityModifier(
        fontScale, density, layoutDirection
    )
    global = out
    return out
}

@Composable
public fun ProvideAccessibilityLocals(content: @Composable () -> Unit) {
    val localDensity = LocalDensity.current
    val localLayoutDirection = LocalLayoutDirection.current

    val provider = remember(localDensity, localLayoutDirection) {
        accessibilityModifier(
            localDensity.fontScale,
            localDensity.density,
            localLayoutDirection
        )
    }
    CompositionLocalProvider(
        LocalAccessibilityModifier provides provider,
        LocalDensity provides Density(
            density = provider.density.floatValue,
            fontScale = provider.fontScale.floatValue
        ),
        LocalLayoutDirection provides provider.layoutDirection.value
    ) {
        ProvidePlatformLocals {
            content()
        }
    }
}

private val FontScaleRange = 0.85f..2.0f
private val DensityRange = 2.55f..4.0f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun AccessibilityBottomSheet(onDismissRequest: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        AccessibilityBottomSheetContent(onDismissRequest = onDismissRequest)
    }
}

@Composable
private fun AccessibilityBottomSheetContent(onDismissRequest: () -> Unit) {
    val accessibilityModifier = LocalAccessibilityModifier.current
    var fontScale by remember { mutableFloatStateOf(accessibilityModifier.fontScale.floatValue) }
    var density by remember { mutableFloatStateOf(accessibilityModifier.density.floatValue) }
    var layoutDirection by remember { mutableStateOf(accessibilityModifier.layoutDirection.value) }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            InfoText("Font Scale = $fontScale")
        }
        item {
            Slider(
                value = fontScale,
                valueRange = FontScaleRange,
                onValueChange = {
                    fontScale = it
                }
            )
        }
        item {
            InfoText("Density = $density")
        }
        item {
            Slider(
                value = density,
                valueRange = DensityRange,
                onValueChange = { density = it }
            )
        }
        item {
            InfoText("Layout Direction = ${layoutDirection.name}")
            Switch(
                Ltr == layoutDirection, onCheckedChange = {
                    layoutDirection = if (it) Ltr
                    else Rtl
                }
            )
        }
        item {
            val windowInfo = LocalWindowInfo.current
            InfoText("Window size = ${windowInfo.containerSize}")
        }
        platformItems()
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    accessibilityModifier.fontScale.floatValue =
                        accessibilityModifier.initialFontScale
                    accessibilityModifier.density.floatValue = accessibilityModifier.initialDensity
                    accessibilityModifier.layoutDirection.value = Ltr
                    onDismissRequest()
                }) {
                    Text("Reset")
                }
                Button(onClick = {
                    accessibilityModifier.fontScale.floatValue = fontScale
                    accessibilityModifier.density.floatValue = density
                    accessibilityModifier.layoutDirection.value = layoutDirection
                    onDismissRequest()
                }) {
                    Text("Apply")
                }
                Button(onClick = {
                    accessibilityModifier.fontScale.floatValue = FontScaleRange.endInclusive
                    accessibilityModifier.density.floatValue = DensityRange.endInclusive
                    onDismissRequest()
                }) {
                    Text("Max")
                }
            }
        }
    }
}

@Composable
internal fun InfoText(text: String) = Text(
    modifier = Modifier.fillMaxWidth(),
    text = text
)
