package shelfify.ui.admin.bookData.components.editBook.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.shelfify.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import shelfify.contracts.field.BookField
import shelfify.ui.admin.bookData.components.button.UploadImage

class EditBookField : BookField {
    @Composable
    override fun CreateField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        visualTransformation: VisualTransformation,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label, color = Color.DarkGray) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.DarkGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 20.dp, 0.dp)
        )
    }

    @Composable
    override fun CreateImageField(
        value: String,
        onValueChange: (String) -> Unit,
        onUriSelected: (Uri) -> Unit,
        label: String,
    ) {
        val context = LocalContext.current
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
        var uploading by remember { mutableStateOf(false) }
        var uploadSuccess by remember { mutableStateOf(false) }
        var uploadError by remember { mutableStateOf<String?>(null) }
        val coroutineScope = rememberCoroutineScope()
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                onUriSelected(it)
                coroutineScope.launch {
                    uploadImageToFirebase(
                        context = context,
                        uri = it,
                        onValueChange = onValueChange,
                        onUploadStarted = { uploading = true },
                        onUploadSuccess = {
                            uploading = false
                            uploadSuccess = true
                        },
                        onUploadError = { error ->
                            uploading = false
                            uploadError = error
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UploadImage().Button {
                launcher.launch("image/*")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                when {
                    selectedImageUri != null -> {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Selected Book Cover",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    value.isNotEmpty() -> {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(value)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Book Cover",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    else -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Default Book Cover",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
            if (uploading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.Gray,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Uploading...", color = Color.Gray)
                }
            }

            if (uploadSuccess) {
                Text(
                    text = "Upload Successful!",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            uploadError?.let { error ->
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }

    @Composable
    fun TitleField(
        value: String,
        onValueChange: (String) -> Unit,
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "Title",
        visualTransformation = VisualTransformation.None,

        )

    @Composable
    fun WriterField(
        value: String,
        onValueChange: (String) -> Unit,
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "Writer",
        visualTransformation = VisualTransformation.None,
    )

    @Composable
    fun IsbnField(
        value: String,
        onValueChange: (String) -> Unit,
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "ISBN",
        visualTransformation = VisualTransformation.None,

        )

    @Composable
    fun PublisherField(
        value: String,
        onValueChange: (String) -> Unit,
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "Publisher",
        visualTransformation = VisualTransformation.None,
    )

    @Composable
    fun PublisherYearField(
        value: String,
        onValueChange: (String) -> Unit,
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "Publisher Year",
        visualTransformation = VisualTransformation.None,
    )

    @Composable
    fun LanguageField(
        value: String,
        onValueChange: (String) -> Unit,
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "Language",
        visualTransformation = VisualTransformation.None,
    )

    @Composable
    fun StockField(
        value: String,
        onValueChange: (String) -> Unit,
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "Stock",
        visualTransformation = VisualTransformation.None,
    )

    @Composable
    fun PagesField(
        value: String,
        onValueChange: (String) -> Unit,
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "Pages",
        visualTransformation = VisualTransformation.None,
    )

    @Composable
    fun DescriptionField(
        value: String,
        onValueChange: (String) -> Unit,
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "Description",
        visualTransformation = VisualTransformation.None,
    )

    @Composable
    fun ImageUrlField(
        value: String,
        onValueChange: (String) -> Unit,
        onUriSelected: (Uri) -> Unit = {},
    ) = CreateImageField(
        value = value,
        onValueChange = onValueChange,
        onUriSelected = onUriSelected,
        label = "Select Image"
    )

    private suspend fun uploadImageToFirebase(
        context: Context,
        uri: Uri,
        onValueChange: (String) -> Unit,
        onUploadStarted: () -> Unit,
        onUploadSuccess: () -> Unit,
        onUploadError: (String) -> Unit,
    ) {
        onUploadStarted()
        try {
            val storageRef: StorageReference = FirebaseStorage.getInstance().reference
            val fileName = "book_images/${System.currentTimeMillis()}_${uri.lastPathSegment}"
            val fileRef = storageRef.child(fileName)

            // Upload the file and await the task
            fileRef.putFile(uri).await()

            // Get the download URL
            val downloadUri = fileRef.downloadUrl.await()
            onValueChange(downloadUri.toString())
            onUploadSuccess()
        } catch (e: Exception) {
            onUploadError(e.message ?: "Image upload failed")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CategoryDropdownField(
        selectedCategory: String,
        onCategorySelected: (String) -> Unit,
    ) {
        val categories = listOf(
            "Action & Adventure",
            "Antiques & Collectibles",
            "Business & Economics",
            "Computer",
            "Engineering",
            "Horror",
            "Family & Relationship",
            "Love & Romance"
        )

        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.DarkGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            onCategorySelected(category)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

