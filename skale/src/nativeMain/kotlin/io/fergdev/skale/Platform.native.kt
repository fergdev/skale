package io.fergdev.skale

import androidx.compose.foundation.lazy.LazyListScope

@androidx.compose.runtime.Composable
internal actual fun ProvidePlatformLocals(content: @androidx.compose.runtime.Composable (() -> Unit)) {
    content()
}

internal actual fun LazyListScope.platformItems() {
    // Noop
}
