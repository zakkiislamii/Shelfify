package shelfify.data

data class ModalConfigWarning(
    val title: String,
    val message: String,
    val confirmText: String = "Ok",
    val onConfirm: () -> Unit,
)