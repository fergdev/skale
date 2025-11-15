package io.fergdev.skale.example

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import io.fergdev.kompare.KReader
import io.fergdev.kompare.TestNameResolver
import io.fergdev.kompare.kompare
import kotlinx.coroutines.test.runTest
import skaleproj.composeapp.generated.resources.Res
import kotlin.test.Test

class MainTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test() = runTest {
        runComposeUiTest {
            setContent {
                App()
            }
            kompare(
                testNameResolver = object : TestNameResolver {
                    override fun getFullTestName(): String = "main"
                },
                reader = RunContentTestReader
            )
        }
    }
}

object RunContentTestReader : KReader {
    override suspend fun readBytes(path: String) = Res.readBytes(path)
}
