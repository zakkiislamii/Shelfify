package shelfify.utils.toast

import android.content.Context
import android.view.Gravity
import android.widget.Toast

class CustomToast {
    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        val toast = Toast.makeText(context, message, duration)
        toast.show()
    }
}
