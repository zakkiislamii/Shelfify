package com.example.shelfify.utils.session

import android.content.Context
import com.example.shelfify.data.UserSession

fun getUserSession(context: Context): UserSession {
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
    val userId = sharedPreferences.getInt("user_id", -1)
    val email = sharedPreferences.getString("email", null)
    val role = sharedPreferences.getString("role", null)
    return UserSession(isLoggedIn, userId, email, role)
}