package shelfify.data.dataMapping

import shelfify.data.baseUI.MemberDataBaseUI

data class MemberDataCardUI(
    override val userId: Int,
    val fullName: String,
    val email: String,
    val faculty: String,
    val phoneNumber: String,
) : MemberDataBaseUI
