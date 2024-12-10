package shelfify.utils.proxy

import android.content.Context
import shelfify.contracts.session.UserSessionData
import shelfify.data.session.UserSession

class RealUserSessionData : UserSessionData {
    override fun getUserSession(context: Context): UserSession {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        return UserSession(
            isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false),
            userId = sharedPreferences.getInt("user_id", 0),
            email = sharedPreferences.getString("email", null),
            role = sharedPreferences.getString("role", null)
        )
    }
    override fun clearSession(context: Context) {
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }
}