package shelfify.utils.proxy

import android.content.Context
import shelfify.contracts.session.UserSessionData
import shelfify.data.session.UserSession

class RealUserSessionData : UserSessionData {
    override fun getUserSession(context: Context): UserSession {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        val userId = sharedPreferences.getInt("user_id", -1)
        val email = sharedPreferences.getString("email", null)
        val role = sharedPreferences.getString("role", null)
        return UserSession(isLoggedIn, userId, email, role)
    }
}
