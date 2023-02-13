package com.projects.bitsoConsumer.navroute

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.projects.bitsoConsumer.composableviews.DetailView
import com.projects.bitsoConsumer.composableviews.TradingChart
import com.projects.bitsoConsumer.composableviews.mainview

@Composable
fun MenuNav(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start") {
//        composable("loading"){ LoadingV() }
        composable("start") {
            mainview(
                navHostController = navController,
                viewModel = hiltViewModel(),
            )
        }
        composable(
            "details/{pair}",
            arguments = listOf(
                navArgument("pair") {
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            DetailView(
                viewModel = hiltViewModel(),
                navController,
                backStackEntry.arguments?.getString("pair"),
            )
        }

        composable(
            "grafica/{pair}",
            arguments = listOf(
                navArgument("pair") {
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            TradingChart(
                viewModel = hiltViewModel(),
                navController,
                backStackEntry.arguments?.getString("pair"),
            )
        }
    }
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}
