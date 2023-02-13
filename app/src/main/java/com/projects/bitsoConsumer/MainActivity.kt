package com.projects.bitsoConsumer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.projects.bitsoConsumer.navroute.MenuNav
import com.projects.bitsoConsumer.ui.theme.BitsoConsumerTheme
import com.projects.bitsoConsumer.mvi.features.books.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BitsoConsumerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    MenuNav(navController)
//                    Greeting("Android", viewModel = hiltViewModel())
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, viewModel: BooksViewModel) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BitsoConsumerTheme {
        Greeting("Android", hiltViewModel())
    }
}
