package com.example.shelfify.utils.helpers

import android.content.Context
import com.example.shelfify.utils.toast.CustomToast

class LoginFieldValidate {
    fun validateFields(context: Context, email: String, password: String): Boolean {
        val toast = CustomToast()

        val fields = listOf(
            "Email" to email,
            "Password" to password
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


        if (password.length < 5) {
            toast.showToast(context, "Password minimal 5 karakter")
            return false
        }

        return true
    }
}