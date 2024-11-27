package shelfify.ui.authUI.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import shelfify.ui.authUI.login.components.HeaderLogin
import shelfify.ui.authUI.login.components.LoginButton
import shelfify.ui.authUI.login.components.LoginField
import shelfify.ui.authUI.login.components.ToForgotPassword
import shelfify.ui.authUI.login.components.ToRegister
import shelfify.utils.helpers.LoginFieldValidate
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowLoginScreen {
    @Composable
    fun Login(
        navController: NavController,
        authViewModel: AuthViewModel,
    ) {
        val emailState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }
        val loginField = LoginField()
        val context = LocalContext.current

        val loginState by authViewModel.loginState.collectAsState()

        LaunchedEffect(loginState) {
            when (loginState) {
                is Result.Success -> {
                    val user = (loginState as Result.Success).data
                    CustomToast().showToast(
                        context = context,
                        message = "Login berhasil! Selamat datang ${user.fullName}"
                    )
                    navController.navigate(Screen.Home.route + "?full_name=${user.fullName}")
                }

                is Result.Error -> {
                    val error = (loginState as Result.Error).message
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
            topBar = { HeaderLogin() }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    loginField.EmailField(
                        value = emailState.value,
                        onValueChange = { emailState.value = it }
                    )

                    loginField.PasswordField(
                        value = passwordState.value,
                        onValueChange = { passwordState.value = it }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 30.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        ToForgotPassword(onClick = { navController.navigate(Screen.ForgotPassword.route) })
                    }

                    LoginButton().Button {
                        if (LoginFieldValidate().validateFields(
                                context,
                                emailState.value,
                                passwordState.value
                            )
                        ) {
                            authViewModel.login(emailState.value, passwordState.value)
                        }
                    }
                    ToRegister(onClick = { navController.navigate(Screen.Register.route) })
                }
            }
        }
    }
}