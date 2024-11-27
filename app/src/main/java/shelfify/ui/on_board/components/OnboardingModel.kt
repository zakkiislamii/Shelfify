package shelfify.ui.on_board.components

import androidx.annotation.DrawableRes
import com.example.shelfify.R


sealed class OnboardingModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
) {
    data object FirstPage : OnboardingModel(
        image = R.drawable.onboard1,
        title = "Discover, Book, and Borrow with Ease",
        description = "Find your favorite book, reserve books online, get approval instantly, and enjoy a seamless borrowing experience with ease!"
    )

    data object SecondPage : OnboardingModel(
        image = R.drawable.onboard2,
        title = "Stay Notified, Stay Organized",
        description = "Get instant updates on your bookings, reminders for pick-up and returns, and a history of all your borrowed books."
    )

    data object ThirdPages : OnboardingModel(
        image = R.drawable.onboard3,
        title = "Welcome to Your Digital Bookshelf",
        description = "Access your personalized profile, track your bookings, and stay on top of the most popular reads with detailed reports and insights"
    )
}