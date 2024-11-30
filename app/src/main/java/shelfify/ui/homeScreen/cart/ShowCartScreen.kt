package shelfify.ui.homeScreen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import shelfify.be.services.viewModel.CartViewModel
import shelfify.contracts.session.UserSessionData
import shelfify.data.CartWithBook
import shelfify.routers.Screen
import shelfify.ui.homeScreen.cart.components.CartBody
import shelfify.ui.homeScreen.cart.components.CartHeader
import shelfify.utils.loading.LoadingIndicator
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import  shelfify.utils.response.Result

class ShowCartScreen {
    @Composable
    fun Cart(navController: NavController, cartViewModel: CartViewModel) {
        val context = LocalContext.current
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        val userId = userSession.userId.toInt()

        val cartsState by cartViewModel.cartsState.collectAsState()

        LaunchedEffect(userId) {
            cartViewModel.getCartsByUserId(userId)
        }

        Scaffold(
            topBar = {
                CartHeader {
                    navController.navigate(Screen.Home.route)
                }
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                when (cartsState) {
                    is Result.Loading -> {
                        LoadingIndicator()
                    }

                    is Result.Success -> {
                        val cartList = (cartsState as Result.Success<List<CartWithBook>>).data
                        CartBody(
                            cartList = cartList,
                            isEmpty = cartList.isEmpty(),
                            cartViewModel = cartViewModel
                        )
                    }

                    is Result.Error -> {
                        Text(
                            text = (cartsState as Result.Error).message,
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }

}