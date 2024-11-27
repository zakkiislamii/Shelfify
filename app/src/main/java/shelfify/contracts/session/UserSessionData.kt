package shelfify.contracts.session

import android.content.Context
import shelfify.data.UserSession

interface UserSessionData {
    fun getUserSession(context: Context): UserSession
}
