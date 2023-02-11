package com.projects.bitsoConsumer.navroute

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.projects.bitsoConsumer.composables.Detailview
import com.projects.bitsoConsumer.composables.mainview


@Composable
fun MenuNav(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start") {
//        composable("loading"){ LoadingV() }
        composable("start") { mainview(navHostController = navController, viewModel = hiltViewModel()) }
        composable(
            "details/{pair}",
            arguments = listOf(
                navArgument("pair") {
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            Detailview(viewModel = hiltViewModel(), navController, backStackEntry.arguments?.getString("pair"))
        }
    }
}
