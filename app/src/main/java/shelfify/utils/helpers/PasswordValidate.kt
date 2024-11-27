package shelfify.utils.helpers

import android.content.Context
import shelfify.utils.toast.CustomToast

class PasswordValidate {
    fun validateFields(context: Context, password: String, confirmPassword: String): Boolean {
        val toast = CustomToast()

        val fields = listOf(
            "Password" to password,
            "Konfirmasi Password" to confirmPassword
        )

        for ((fieldName, value) in fields) {
            if (value.isBlank()) {
                toast.showToast(context, "$fieldName tidak boleh kosong")
                return false
            }
        }

        if (password.length < 5) {
            toast.showToast(context, "Password minimal 5 karakter")
            return false
        }

        if (password != confirmPassword) {
            toast.showToast(context, "Password baru dan konfirmasi password baru tidak sama")
            return false
        }

        return true
    }
}