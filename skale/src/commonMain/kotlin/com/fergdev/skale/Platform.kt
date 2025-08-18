package com.fergdev.skale

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable

@Composable
internal expect fun ProvidePlatformLocals(
     content: @Composable () -> Unit
)

internal expect fun LazyListScope.platformItems()
