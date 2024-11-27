package shelfify.utils.helpers

import android.content.Context
import shelfify.utils.toast.CustomToast

class ForgotPasswordValidate {
    fun validateFields(context: Context, email: String): Boolean {
        val toast = CustomToast()

        val fields = listOf(
            "Email" to email
        )

        for ((fieldName, value) in fields) {
            if (value.isBlank()) {
                toast.showToast(context, "$fieldName tidak boleh kosong")
                return false
            }
        }

        if (!email.contains("@")) {
            toast.showToast(context, "Format email tidak valid, harus mengandung '@'")
            return false
        }
        return true
    }
}