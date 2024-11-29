package shelfify.ui.authUI.forgotPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.routers.Screen
import shelfify.ui.authUI.forgotPassword.components.ForgotPasswordButton
import shelfify.ui.authUI.forgotPassword.components.ForgotPasswordField
import shelfify.ui.authUI.forgotPassword.components.ForgotPasswordHeader
import shelfify.utils.helpers.ForgotPasswordValidate
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowForgotPasswordScreen {
    @Composable
    fun ForgotPassword(navController: NavController, authViewModel: AuthViewModel) {
        val emailState = remember { mutableStateOf("") }
        val context = LocalContext.current
        val forgotPasswordState by authViewModel.getUserByEmailState.collectAsState()

        LaunchedEffect(forgotPasswordState) {
            when (forgotPasswordState) {
                is Result.Success -> {
                    val user = (forgotPasswordState as Result.Success).data
                    CustomToast().showToast(
                        context = context,
                        message = "Halo ${user.email}! Silahkan ubah password"
                    )
                }

                is Result.Error -> {
                    val error = (forgotPasswordState as Result.Error).message
                    CustomToast().showToast(
                        context = context,
                        message = error
                    )
                }

                Result.Loading -> {
                    // Handle loading state if needed
                }
            }
        }

        Scaffold(
            topBar = {
                ForgotPasswordHeader(onClick = { navController.navigate(Screen.Login.route) })
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(paddingValues),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 150.dp, start = 10.dp, end = 10.dp, bottom = 0.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ForgotPasswordField().apply {
                            EmailField(
                                value = emailState.value,
                                onValueChange = { emailState.value = it }
                            )
                        }

                        ForgotPasswordButton().Button {
                            if (ForgotPasswordValidate().validateFields(
                                    context,
                                    emailState.value
                                )
                            ) {
                                authViewModel.getUserByEmail(emailState.value)
                                navController.navigate(Screen.ChangePassword.route + "?email=${emailState.value}")
                            }
                        }
                    }
                }
            }
        )
    }
}
