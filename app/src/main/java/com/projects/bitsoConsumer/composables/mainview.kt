package com.projects.bitsoConsumer.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projects.bitsoConsumer.composableitems.MoneyCard
import com.projects.bitsoConsumer.models.DetailedPayload
import com.projects.bitsoConsumer.mvi.features.books.MainContract
import com.projects.bitsoConsumer.viewmodels.BooksViewModel
import com.projects.mvi.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun Mainview(navHostController: NavHostController, viewModel: BooksViewModel) {
    val scope = rememberCoroutineScope()

    var lista: List<DetailedPayload> by remember {
        mutableStateOf(emptyList())
    }

    var Loading by remember {
        mutableStateOf(false)
    }

    var updates by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        while (true) {
            delay(5.seconds)
            viewModel.setEvent(event = MainContract.Event.OnNewClick)
        }
    }

    LaunchedEffect(scope) {
        viewModel.uiState.collect {
            when (it.getInfo) {
                MainContract.BitsoApiState.Idle -> {}
                is MainContract.BitsoApiState.Loading -> Loading=it.getInfo.status
                is MainContract.BitsoApiState.Success -> lista = it.getInfo.link
                is MainContract.BitsoApiState.tick -> updates = it.getInfo.link
            }
        }
    }

    with(viewModel) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.criptoinfo),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    )
                },
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.90F),
                contentPadding = PaddingValues(16.dp),
                state = rememberLazyListState(),
            ) {
                itemsIndexed(lista) { _, list ->
                    MoneyCard(this@with, list, navHostController)
                }
            }
        }
    }
}