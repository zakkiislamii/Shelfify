package shelfify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.initialize
import shelfify.routers.AppNavHost
import shelfify.ui.onBoard.OnboardingScreen
import shelfify.ui.onBoard.OnboardingUtils
import shelfify.ui.theme.ShelfifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
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
