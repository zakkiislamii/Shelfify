package shelfify.ui.components.card

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.contracts.card.CardReserveComponent
import shelfify.contracts.enumerations.Status
import shelfify.data.dataMapping.MemberReserveCardUI
import shelfify.routers.Screen
import shelfify.ui.admin.reservationData.components.ButtonReserveMember
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Locale

class ReserveDataCard : CardReserveComponent<MemberReserveCardUI> {
    @Composable
    override fun CreateCard(
        item: MemberReserveCardUI,
        navController: NavController,
        reservationViewModel: ReservationViewModel,
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    0.9.dp,
                    Color.DarkGray,
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy\nHH:mm", Locale.getDefault())
            val formattedTime = item.createdAt.let { dateFormat.format(it) } ?: "-"
            val context = LocalContext.current
            val updateStatus by reservationViewModel.updateStatus.collectAsState()
            val bookTitles = item.title.split(",")

            LaunchedEffect(updateStatus) {
                when (updateStatus) {
                    is Result.Success -> {
                        reservationViewModel.resetUpdateStatus()
                        CustomToast().showToast(
                            context,
                            "Reservation updated successfully",
                        )
                    }

                    is Result.Error -> {
                        val errorMessage = (updateStatus as Result.Error).message
                        reservationViewModel.resetUpdateStatus()
                        CustomToast().showToast(context, "Error: $errorMessage")
                    }

                    is Result.Loading -> {
                        // Handle loading state if needed
                    }

                    null -> {
                        // Initial state, do nothing
                    }
                }
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = item.reservationStatus.toString(),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1,
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = item.fullName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        when (item.reservationStatus) {
                            Status.PENDING -> {
                                Text(
                                    text = "Number of Books Reserved: ${item.totalReserve}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    fontSize = 9.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            Status.BORROWED -> {
                                Text(
                                    text = "Number of Books Borrowed: ${item.totalReserve}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    fontSize = 9.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            Status.RETURNED -> {
                                Text(
                                    text = "Number of Books Returned: ${item.totalReserve}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    fontSize = 9.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            else -> {
                                Text(
                                    text = "Number of Books Rejected: ${item.totalReserve}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    fontSize = 9.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                        }
                        Text(
                            text = formattedTime,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                    }
                }
                if (item.reservationStatus == Status.PENDING) {
                    ButtonReserveMember().ViewReservationButton {
                        try {
                            val bookImage = item.bookImage ?: ""
                            val route =
                                Screen.Admin.ViewDetails.route.replace("{fullName}", item.fullName)
                                    .replace("{title}", item.title)
                                    .replace("{writer}", item.writer)
                                    .replace("{reservationId}", item.reservationId)
                                    .replace(
                                        "{bookImage}",
                                        URLEncoder.encode(bookImage, "UTF-8")
                                    )
                                    .replace("{userId}", item.userId.toString())
                                    .replace("{bookId}", item.bookId)
                            navController.navigate(route)

                        } catch (e: Exception) {
                            CustomToast().showToast(
                                context,
                                "Error navigating: ${e.message}",
                            )
                            Log.d("error nav", e.message.toString())
                        }
                    }
                } else if (item.reservationStatus == Status.BORROWED) {
                    ButtonReserveMember().ReturnedButton {
                        if (item.reservationId.isNotEmpty()) {
                            reservationViewModel.updateReservationStatus(
                                userId = item.userId,
                                reservationIds = item.reservationId,
                                newStatus = Status.RETURNED,
                                bookTitles = bookTitles,
                                bookIds = item.bookId
                            )
                        } else {
                            CustomToast().showToast(
                                context,
                                "Invalid reservation ID",
                            )
                        }

                    }
                }
            }
        }
    }
}
