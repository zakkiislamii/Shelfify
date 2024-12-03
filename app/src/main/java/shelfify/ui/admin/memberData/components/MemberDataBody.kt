package shelfify.ui.admin.memberData.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.contracts.enumerations.Role
import shelfify.data.dataMapping.MemberDataCardUI
import shelfify.ui.components.card.MemberDataCard
import shelfify.utils.loading.LoadingIndicator


@Composable
fun MemberDataBody(adminViewModel: AdminViewModel, navController: NavController) {
    val users by adminViewModel.users.collectAsState()

    LaunchedEffect(Unit) {
        adminViewModel.getAllUsers()
    }

    val filteredUsers = users.filter { it.role != Role.ADMIN }

    if (filteredUsers.isEmpty()) {
        LoadingIndicator()
    } else {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(filteredUsers.size) { index ->
                val user = filteredUsers[index]
                val memberData = MemberDataCardUI(
                    userId = user.userId,
                    fullName = user.fullName,
                    email = user.email,
                    faculty = user.faculty,
                    phoneNumber = user.phoneNumber
                )

                MemberDataCard().CreateCard(
                    item = memberData,
                    onHistoryClick = {
                        navController.navigate("historyMember/${user.userId}/${user.fullName}")
                    },
                    onDeleteClick = {
                        adminViewModel.deleteUser(user.userId)
                    }
                )
            }
        }
    }
}
