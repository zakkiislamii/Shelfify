package shelfify.data.modal

data class ModalConfig(
    val title: String,
    val message: String,
    val confirmText: String = "Yes",
    val cancelText: String = "No",
    val onConfirm: () -> Unit,
    val onCancel: () -> Unit,
)