package shelfify.data.dataMapping

sealed class LogoutEvent {
    data object Initial : LogoutEvent()
    data object Loading : LogoutEvent()
    data object Success : LogoutEvent()
    data class Error(val message: String) : LogoutEvent()
}