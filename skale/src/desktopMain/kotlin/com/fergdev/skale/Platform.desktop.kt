package com.fergdev.skale

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable

@Composable
internal actual fun ProvidePlatformLocals(content: @Composable (() -> Unit)) {
    content()
}

@Composable
internal actual fun LazyListScope.platformItems() {
}