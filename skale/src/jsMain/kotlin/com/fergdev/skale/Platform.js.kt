package com.fergdev.skale

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable

@Composable
internal actual fun ProvidePlatformLocals(
    content: @Composable () -> Unit
) {
    content()
}

internal actual fun LazyListScope.platformItems() {
    // Noop
}
