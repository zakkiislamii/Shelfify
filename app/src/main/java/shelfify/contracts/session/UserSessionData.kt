package shelfify.contracts.session

import android.content.Context
import shelfify.data.session.UserSession

interface UserSessionData {
    fun getUserSession(context: Context): UserSession
}
