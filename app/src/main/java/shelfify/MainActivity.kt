package shelfify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import shelfify.ui.on_board.OnboardingScreen
import shelfify.routers.AppNavHost
import shelfify.ui.on_board.OnboardingUtils
import shelfify.ui.theme.ShelfifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ShelfifyTheme {
                val isOnboardingCompleted = remember {
                    mutableStateOf(OnboardingUtils(this).isOnboardingCompleted())
                }

                if (isOnboardingCompleted.value) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                } else {
                    ShowOnboardingScreen {
                        OnboardingUtils(this@MainActivity).setOnboardingCompleted()
                        isOnboardingCompleted.value = true
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowOnboardingScreen(onFinished: () -> Unit) {
    OnboardingScreen(onFinished = onFinished)
}
