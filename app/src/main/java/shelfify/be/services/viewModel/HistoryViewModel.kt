package shelfify.be.services.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class HistoryViewModel : ViewModel() {
    private val _countdown = MutableStateFlow<String?>(null)
    val countdown = _countdown.asStateFlow()
    private var countdownJob: Job? = null

    fun startCountdown(type: String) {
        // Batalkan job sebelumnya
        countdownJob?.cancel()

        countdownJob = viewModelScope.launch {
            try {
                while (isActive) {
                    val jakartaCalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"))
                    val deadline = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"))

                    when (type) {
                        "Pending" -> deadline.add(Calendar.DAY_OF_MONTH, 3)
                        "Borrowed" -> deadline.add(Calendar.DAY_OF_MONTH, 7)
                    }

                    val diff = deadline.timeInMillis - jakartaCalendar.timeInMillis

                    if (diff > 0) {
                        val days = TimeUnit.MILLISECONDS.toDays(diff)
                        val hours = TimeUnit.MILLISECONDS.toHours(diff) % 24
                        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60
                        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60

                        _countdown.value = "${days}d : ${hours}h : ${minutes}m : ${seconds}s"
                    } else {
                        _countdown.value = "Time expired"
                    }

                    delay(1000)
                }
            } catch (e: Exception) {
                Log.e("HistoryViewModel", "Countdown error: ${e.message}", e)
                _countdown.value = "Error"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}
