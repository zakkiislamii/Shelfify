package shelfify.ui.member.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.MemberViewModel
import shelfify.contracts.session.UserSessionData
import shelfify.routers.Screen
import shelfify.ui.member.profile.components.EditProfileBody
import shelfify.ui.member.profile.components.ProfileBody
import shelfify.ui.member.profile.components.ProfileHeader
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowProfileScreen {
    @Composable
    fun ProfileScreen(
        navController: NavController,
        authViewModel: AuthViewModel,
        memberViewModel: MemberViewModel,
    ) {
        val context = LocalContext.current
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        val profileState = authViewModel.getUserByEmailState.collectAsState()
        val updateProfileState = memberViewModel.updateUserState.collectAsState()
        var isEditing by remember { mutableStateOf(false) }
        var fullName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var faculty by remember { mutableStateOf("") }
        var profileImage by remember { mutableStateOf<String?>(null) }
        var isLoading by remember { mutableStateOf(false) }

        LaunchedEffect(userSession.email) {
            userSession.email?.let { email ->
                authViewModel.getUserByEmail(email)
            }
        }

        LaunchedEffect(updateProfileState.value) {
            when (val state = updateProfileState.value) {
                is Result.Success -> {
                    memberViewModel.resetUpdateProfileState()
                    CustomToast().showToast(
                        context = context,
                        message = "Profile updated successfully"
                    )
                    isEditing = false
                    userSession.email?.let { email ->
                        authViewModel.getUserByEmail(email)
                    }
                    isLoading = false
                }

                is Result.Error -> {
                    memberViewModel.resetUpdateProfileState()
                    CustomToast().showToast(
                        context = context,
                        message = state.message
                    )
                    isLoading = false
                }

                else -> {}
            }
        }

        when (val state = profileState.value) {
            is Result.Success -> {
                fullName = state.data.fullName
                email = state.data.email
                phoneNumber = state.data.phoneNumber
                address = state.data.address
                faculty = state.data.faculty
                profileImage = state.data.profileImage
            }

            is Result.Error -> {
                CustomToast().showToast(
                    context = context,
                    message = state.message
                )
            }

            Result.Loading -> {}
        }

        Scaffold(
            topBar = {
                ProfileHeader(onClick = {
                    navController.navigate(Screen.Member.Setting.route) {
                        launchSingleTop = true
                    }
                })
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(20.dp)
            ) {
                if (isEditing) {
                    EditProfileBody(
                        email = email,
                        fullName = fullName,
                        phoneNumber = phoneNumber,
                        address = address,
                        faculty = faculty,
                        profileImage = profileImage,
                        isLoading = isLoading,
                        onSave = { newFullName, _, newPhone, newAddress, newFaculty, newImageUri ->
                            isLoading = true
                            memberViewModel.updateUser(
                                userId = userSession.userId,
                                fullName = newFullName,
                                email = email,
                                phoneNumber = newPhone,
                                address = newAddress,
                                faculty = newFaculty,
                                newImageUri = newImageUri
                            )
                        },
                        onCancel = {
                            isEditing = false
                        }
                    )
                } else {
                    ProfileBody(
                        email = email,
                        fullName = fullName,
                        phoneNumber = phoneNumber,
                        address = address,
                        faculty = faculty,
                        profileImage = profileImage,
                        onEditClick = {
                            isEditing = true
                        }
                    )
                }
            }
        }
    }
}