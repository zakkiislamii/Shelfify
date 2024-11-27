package shelfify.ui.authUI.changePassword

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
import shelfify.routers.Screen
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.ui.authUI.changePassword.components.ChangePasswordButton
import shelfify.ui.authUI.changePassword.components.ChangePasswordField
import shelfify.ui.authUI.changePassword.components.ChangePasswordHeader
import shelfify.utils.helpers.PasswordValidate
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowChangePasswordScreen {
    @Composable
    fun ChangePassword(navController: NavController, authViewModel: AuthViewModel) {
        // Ambil email dari argumen navigasi
        val emailState = navController.currentBackStackEntry?.arguments?.getString("email") ?: ""
        val passwordState = remember { mutableStateOf("") }
        val confirmPasswordState = remember { mutableStateOf("") }
        val context = LocalContext.current
        val changePasswordState by authViewModel.changePasswordState.collectAsState()

        LaunchedEffect(changePasswordState) {
            when (changePasswordState) {
                is Result.Success -> {
                    val user = (changePasswordState as Result.Success).data
                    CustomToast().showToast(
                        context = context,
                        message = "Password telah diubah! Silahkan login ${user.email}"
                    )
                    navController.navigate(Screen.Login.route)
                }

                is Result.Error -> {
                    val error = (changePasswordState as Result.Error).message
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
                ChangePasswordHeader(onClick = { navController.navigate(Screen.ForgotPassword.route) })
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
                        // Input fields inside the content area
                        ChangePasswordField().apply {
                            PasswordField(
                                value = passwordState.value,
                                onValueChange = { passwordState.value = it }
                            )
                            ConfirmPasswordField(
                                value = confirmPasswordState.value,
                                onValueChange = { confirmPasswordState.value = it }
                            )
                        }

                        ChangePasswordButton().Button {
                            if (PasswordValidate().validateFields(
                                    context,
                                    passwordState.value,
                                    confirmPasswordState.value
                                )
                            ) {
                                authViewModel.changePassword(
                                    emailState,
                                    passwordState.value
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
