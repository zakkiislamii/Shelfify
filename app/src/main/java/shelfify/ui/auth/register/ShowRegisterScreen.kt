package shelfify.ui.auth.register

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
import shelfify.data.FacultyData
import shelfify.routers.Screen
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.ui.auth.register.components.FacultyDropdown
import shelfify.ui.auth.register.components.RegisterButton
import shelfify.ui.auth.register.components.RegisterField
import shelfify.ui.auth.register.components.RegisterHeader
import shelfify.ui.auth.register.components.ToLogin
import shelfify.utils.helpers.RegisterFieldValidate
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowRegisterScreen {
    @Composable
    fun Register(navController: NavController, authViewModel: AuthViewModel) {
        val fullNameState = remember { mutableStateOf("") }
        val phoneNumberState = remember { mutableStateOf("") }
        val addressState = remember { mutableStateOf("") }
        val facultyState = remember { mutableStateOf("") }
        val emailState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }
        val confirmPasswordState = remember { mutableStateOf("") }

        val registerState by authViewModel.registerState.collectAsState()
        val context = LocalContext.current
        val scrollState = rememberScrollState()

        // Handle registration result in LaunchedEffect
        LaunchedEffect(registerState) {
            when (registerState) {
                is Result.Success -> {
                    val user = (registerState as Result.Success).data
                    CustomToast().showToast(
                        context = context,
                        message = "Registration Successful: ${user.fullName}",
                    )
                    navController.navigate(Screen.Login.route)
                }

                is Result.Error -> {
                    val error = (registerState as Result.Error).message
                    CustomToast().showToast(
                        context = context,
                        message = "Registration Failed: $error",
                    )
                }

                Result.Loading -> {
                    // Handle loading if needed
                }
            }
        }

        Scaffold(
            topBar = {
                RegisterHeader(onClick = { navController.navigate(Screen.Login.route) })
            }
        ) { paddingValues ->
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
                    // Input fields
                    RegisterField().apply {
                        FullNameField(
                            value = fullNameState.value,
                            onValueChange = { fullNameState.value = it }
                        )
                        EmailField(
                            value = emailState.value,
                            onValueChange = { emailState.value = it }
                        )
                        PhoneNumberField(
                            value = phoneNumberState.value,
                            onValueChange = { phoneNumberState.value = it }
                        )
                        AddressField(
                            value = addressState.value,
                            onValueChange = { addressState.value = it }
                        )
                    }

                    FacultyDropdown().CreateDropdownField(
                        value = facultyState.value,
                        onValueChange = { facultyState.value = it },
                        label = "Faculty",
                        options = FacultyData.facultyOptions
                    )

                    RegisterField().apply {
                        PasswordField(
                            value = passwordState.value,
                            onValueChange = { passwordState.value = it }
                        )
                        ConfirmPasswordField(
                            value = confirmPasswordState.value,
                            onValueChange = { confirmPasswordState.value = it }
                        )
                    }

                    val validator = RegisterFieldValidate()

                    // Register Button
                    RegisterButton().Button {
                        if (validator.validateFields(
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
                                fullName = fullNameState.value,
                                email = emailState.value,
                                phoneNumber = phoneNumberState.value,
                                address = addressState.value,
                                faculty = facultyState.value,
                                password = passwordState.value
                            )
                        }
                    }

                    ToLogin(onClick = { navController.navigate(Screen.Login.route) })
                }
            }
        }
    }
}