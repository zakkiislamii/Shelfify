package shelfify.ui.homeScreen.history.member

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import shelfify.be.services.viewModel.HistoryViewModel
import shelfify.ui.theme.MainColor

@Composable
fun HistoryHeader(viewModel: HistoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var selectedTab by remember { mutableStateOf("Pending") }
    val countdown by viewModel.countdown.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedTab) {
        if (selectedTab in listOf("Pending", "Borrowed")) {
            scope.launch {
                viewModel.startCountdown(selectedTab)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            text = "History",
            modifier = Modifier.padding(16.dp),
            color = MainColor,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Pending", "Borrowed", "Rejected", "Returned").forEach { tab ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.clickable { selectedTab = tab }
                ) {
                    Text(
                        text = tab,
                        fontSize = 10.sp,
                        color = if (selectedTab == tab) MainColor else Color.Gray,
                        fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    if (selectedTab == tab) {
                        HorizontalDivider(
                            modifier = Modifier.width(40.dp),
                            thickness = 2.dp,
                            color = MainColor
                        )
                    }
                }
            }
        }

        if (selectedTab in listOf("Pending", "Borrowed")) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(0.5.dp)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (selectedTab == "Pending")
                        "Please pick up books from the Library before"
                    else "Please return books to the Library before",
                    color = Color.DarkGray,
                    fontSize = 10.sp
                )
                Text(
                    text = countdown ?: "",
                    color = Color.DarkGray,
                    fontSize = 13.sp
                )
            }
        } else if (selectedTab in listOf("Rejected", "Returned")) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(0.5.dp)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "1 Book",
                    color = Color.DarkGray,
                    fontSize = 13.sp
                )
            }
        }
    }
}
