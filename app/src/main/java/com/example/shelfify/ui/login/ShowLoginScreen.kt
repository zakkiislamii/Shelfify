package com.example.shelfify.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shelfify.ui.login.buttonLogin.LoginButton

class ShowLoginScreen {
    @Preview
    @Composable
    fun Login() {
        val emailState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }
        val loginField = LoginField()

        Scaffold(
            topBar = {
                HeaderLogin()
            },
            content = { paddingValues ->
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
                            ToForgotPassword()
                        }
                        LoginButton().Button {
                        }
                        ToRegister()
                    }
                }
            }
        )
    }
}

