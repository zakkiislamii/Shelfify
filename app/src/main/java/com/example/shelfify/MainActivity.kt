package com.example.shelfify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.shelfify.ui.login.ShowLoginScreen
import com.example.shelfify.ui.on_board.OnboardingScreen
import com.example.shelfify.ui.on_board.OnboardingUtils
import com.example.shelfify.ui.theme.ShelfifyTheme

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
                    ShowLoginScreen().Login()
                } else {
                    ShowOnboardingScreen {
                        OnboardingUtils(this).setOnboardingCompleted()
                        isOnboardingCompleted.value = true
                    }
                }

//                ShowOnboardingScreen {
//                    OnboardingUtils(this).setOnboardingCompleted()
//                    isOnboardingCompleted.value = true
//                }

            }
        }
    }
}


@Composable
private fun ShowOnboardingScreen(onFinished: () -> Unit) {
    OnboardingScreen(onFinished = onFinished)
}


