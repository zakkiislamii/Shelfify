package shelfify.ui.member.history.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.contracts.enumerations.Status
import shelfify.ui.theme.MainColor

@Composable
fun HistoryHeader(
    selectedTab: Status,
    onTabSelected: (Status) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            text = "History",
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 24.dp),
            color = MainColor,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(
                Status.PENDING to "Pending",
                Status.BORROWED to "Borrowed",
                Status.REJECTED to "Rejected",
                Status.RETURNED to "Returned"
            ).forEach { (tab, displayText) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable { onTabSelected(tab) }
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = displayText,
                        fontSize = 10.sp,
                        color = if (selectedTab == tab) MainColor else Color.Gray,
                        fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    if (selectedTab == tab) {
                        HorizontalDivider(
                            modifier = Modifier.width(48.dp),
                            thickness = 2.dp,
                            color = MainColor
                        )
                    }
                }
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
    }
}