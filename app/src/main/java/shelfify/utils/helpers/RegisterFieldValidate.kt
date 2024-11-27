package shelfify.utils.helpers

import android.content.Context
import shelfify.utils.toast.CustomToast

class RegisterFieldValidate {
    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return try {
            phoneNumber.toLong()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun validateFields(
        context: Context,
        fullName: String,
        email: String,
        phoneNumber: String,
        address: String,
        faculty: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        val toast = CustomToast()

        val fields = listOf(
            "Nama Lengkap" to fullName,
            "Email" to email,
            "Nomor Telepon" to phoneNumber,
            "Alamat" to address,
            "Fakultas" to faculty,
            "Password" to password,
            "Konfirmasi Password" to confirmPassword
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

        if (!isValidPhoneNumber(phoneNumber)) {
            toast.showToast(context, "Nomor telepon harus berupa angka")
            return false
        }

        if (phoneNumber.length < 10) {
            toast.showToast(context, "Nomor telepon minimal 10 digit")
            return false
        }

        if (password.length < 5) {
            toast.showToast(context, "Password minimal 5 karakter")
            return false
        }

        if (password != confirmPassword) {
            toast.showToast(context, "Password dan konfirmasi password tidak sama")
            return false
        }

        return true
    }
}