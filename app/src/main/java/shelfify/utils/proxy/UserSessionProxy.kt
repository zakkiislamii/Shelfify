package shelfify.utils.proxy

import android.content.Context
import shelfify.contracts.session.UserSessionData
import shelfify.data.UserSession

class UserSessionProxy(private val realUserSessionData: UserSessionData) : UserSessionData {
    override fun getUserSession(context: Context): UserSession {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        if (!isLoggedIn) {
            throw IllegalStateException("User not logged in")
        }

        return realUserSessionData.getUserSession(context)
    }
}
