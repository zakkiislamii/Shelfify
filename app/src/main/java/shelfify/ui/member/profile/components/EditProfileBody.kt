package shelfify.ui.member.profile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.shelfify.R
import shelfify.data.faculty.FacultyData
import shelfify.ui.member.profile.components.customButton.CustomButton
import shelfify.ui.member.profile.components.customField.CustomField
import shelfify.utils.loading.LoadingIndicator

@Composable
fun EditProfileBody(
    email: String,
    fullName: String,
    phoneNumber: String,
    address: String,
    faculty: String,
    isLoading: Boolean,
    profileImage: String?,
    onSave: (String, String, String, String, String, Uri?) -> Unit,
    onCancel: () -> Unit,
) {
    var editedFullName by remember { mutableStateOf(fullName) }
    var editedPhoneNumber by remember { mutableStateOf(phoneNumber) }
    var editedAddress by remember { mutableStateOf(address) }
    var editedFaculty by remember { mutableStateOf(faculty) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val customField = CustomField()
    val customButton = CustomButton()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedImageUri = it }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        if (isLoading) {
            LoadingIndicator()
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Edit Profile",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                customButton.CreateButton(
                    text = "Cancel",
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    borderColor = Color.Black,
                    textStyle = TextStyle(fontWeight = FontWeight.Medium),
                    fontSize = 14,
                    onClick = onCancel
                )

                customButton.CreateButton(
                    text = "Save",
                    backgroundColor = Color.Black,
                    textColor = Color.White,
                    borderColor = Color.Black,
                    textStyle = TextStyle(fontWeight = FontWeight.Medium),
                    fontSize = 14,
                    onClick = {
                        onSave(
                            editedFullName,
                            email,
                            editedPhoneNumber,
                            editedAddress,
                            editedFaculty,
                            selectedImageUri
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(150.dp)
                .clip(CircleShape)
                .background(color = Color(0xFFD9D9D9))
                .border(2.dp, Color.Black, CircleShape)
                .clickable { launcher.launch("image/*") }
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Selected Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else if (!profileImage.isNullOrEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(profileImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Default Profile",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Change Photo",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(20.dp),
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Custom Fields
        customField.CreateField(
            value = editedFullName,
            onValueChange = { editedFullName = it },
            label = "Full Name",
            visualTransformation = VisualTransformation.None,
            isPasswordField = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        customField.CreateField(
            value = email,
            onValueChange = { },
            label = "Email",
            visualTransformation = VisualTransformation.None,
            isPasswordField = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Faculty Dropdown
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = editedFaculty,
                onValueChange = { },
                label = { Text("Faculty") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { isDropdownExpanded = !isDropdownExpanded }) {
                        Icon(
                            imageVector = if (isDropdownExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Dropdown Arrow",
                            tint = Color.DarkGray,
                        )

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isDropdownExpanded = true }
            )

            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                FacultyData.facultyOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            editedFaculty = option
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        customField.CreateField(
            value = editedPhoneNumber,
            onValueChange = { editedPhoneNumber = it },
            label = "Phone Number",
            visualTransformation = VisualTransformation.None,
            isPasswordField = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        customField.CreateField(
            value = editedAddress,
            onValueChange = { editedAddress = it },
            label = "Address",
            visualTransformation = VisualTransformation.None,
            isPasswordField = false
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}