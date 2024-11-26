package com.example.shelfify.ui.authUI.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shelfify.data.FacultyData
import com.example.shelfify.services.state.RegisterState
import com.example.shelfify.services.viewModel.AuthViewModel
import com.example.shelfify.ui.authUI.register.components.FacultyDropdown
import com.example.shelfify.ui.authUI.register.components.RegisterButton
import com.example.shelfify.ui.authUI.register.components.RegisterField
import com.example.shelfify.ui.authUI.register.components.RegisterHeader
import com.example.shelfify.ui.authUI.register.components.ToLogin
import com.example.shelfify.utils.helpers.RegisterFieldValidate
import com.example.shelfify.utils.toast.CustomToast

class ShowRegisterScreen {
    @Composable
    fun Register(onNavigateToLogin: () -> Unit, authViewModel: AuthViewModel) {
        val fullNameState = remember { mutableStateOf("") }
        val phoneNumberState = remember { mutableStateOf("") }
        val addressState = remember { mutableStateOf("") }
        val facultyState = remember { mutableStateOf("") }
        val emailState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }
        val confirmPasswordState = remember { mutableStateOf("") }
        val registerField = RegisterField()
        val facultyDropdown = FacultyDropdown()
        val context = LocalContext.current
        val scrollState = rememberScrollState()
        val isLoadingState = authViewModel.isLoading.collectAsState().value

        val registerState = authViewModel.registerState.collectAsState().value

        Scaffold(
            topBar = {
                RegisterHeader(onClick = { onNavigateToLogin() })
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
                            .verticalScroll(scrollState)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        registerField.FullNameField(
                            value = fullNameState.value,
                            onValueChange = { fullNameState.value = it },
                        )
                        registerField.EmailField(
                            value = emailState.value,
                            onValueChange = { emailState.value = it },
                        )
                        registerField.PhoneNumberField(
                            value = phoneNumberState.value,
                            onValueChange = { phoneNumberState.value = it },
                        )
                        registerField.AddressField(
                            value = addressState.value,
                            onValueChange = { addressState.value = it },
                        )
                        facultyDropdown.CreateDropdownField(
                            value = facultyState.value,
                            onValueChange = { facultyState.value = it },
                            label = "Faculty",
                            options = FacultyData.facultyOptions
                        )
                        registerField.PasswordField(
                            value = passwordState.value,
                            onValueChange = { passwordState.value = it }
                        )
                        registerField.ConfirmPasswordField(
                            value = confirmPasswordState.value,
                            onValueChange = { confirmPasswordState.value = it }
                        )
                        val validator = RegisterFieldValidate()

                        RegisterButton().Button {
                            if (!isLoadingState && validator.validateFields(
                                    context = context,
                                    fullName = fullNameState.value,
                                    email = emailState.value,
                                    phoneNumber = phoneNumberState.value,
                                    address = addressState.value,
                                    faculty = facultyState.value,
                                    password = passwordState.value,
                                    confirmPassword = confirmPasswordState.value
                                )
                            ) {
                                authViewModel.register(
                                    fullNameState.value,
                                    emailState.value,
                                    phoneNumberState.value,
                                    addressState.value,
                                    facultyState.value,
                                    passwordState.value
                                )
                            }
                        }

                        // Handle Registration State
                        when (registerState) {
                            is RegisterState.Success -> {
                                CustomToast().showToast(
                                    context = context,
                                    message = "Registered successfully"
                                )
                                onNavigateToLogin()
                            }

                            is RegisterState.Error -> {
                                CustomToast().showToast(
                                    context = context,
                                    message = "Registration failed: ${registerState.message}"
                                )
                            }

                            else -> {}
                        }

                        ToLogin(onClick = { onNavigateToLogin() })
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun RegisterPreview() {
    val authViewModel: AuthViewModel = viewModel() // Gunakan AuthViewModel untuk preview
    ShowRegisterScreen().Register(onNavigateToLogin = {}, authViewModel = authViewModel)
}
