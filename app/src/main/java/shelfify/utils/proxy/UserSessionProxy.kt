package shelfify.utils.proxy

import android.content.Context
import shelfify.contracts.session.UserSessionData
import shelfify.data.session.UserSession

class UserSessionProxy(private val realUserSessionData: UserSessionData) : UserSessionData {
    override fun getUserSession(context: Context): UserSession {
        val session = realUserSessionData.getUserSession(context)
        if (!session.isLoggedIn) {
            clearSession(context)
            return UserSession(
                isLoggedIn = false,
                userId = 0,
                email = null,
                role = null
            )
        }
        return session
    }
    override fun clearSession(context: Context) {
        realUserSessionData.clearSession(context)
    }
}

