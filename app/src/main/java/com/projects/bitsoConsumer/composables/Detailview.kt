package com.projects.bitsoConsumer.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projects.bitsoConsumer.composableitems.ItemTrading
import com.projects.bitsoConsumer.composableitems.MoneyDetails
import com.projects.bitsoConsumer.models.bitsotickers.PayloadTickers
import com.projects.bitsoConsumer.models.trading.PayloadTrades
import com.projects.bitsoConsumer.mvi.features.TradeInfo.DetailsContract
import com.projects.bitsoConsumer.support.shortToken
import com.projects.bitsoConsumer.viewmodels.BooksDetailsViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun Detailview(viewModel: BooksDetailsViewModel, navController: NavHostController, string: String?) {
    val scope = rememberCoroutineScope()

    var askbidsList: List<PayloadTickers> by remember {
        mutableStateOf(emptyList())
    }

    var TradesList: List<PayloadTrades> by remember {
        mutableStateOf(emptyList())
    }

    println("voy a recibir $askbidsList")

    with(viewModel) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(5.seconds)
                string?.let {
                    setEvent(DetailsContract.Event.OnNewClick(it))
                }
            }
        }

        LaunchedEffect(scope) {
            viewModel.uiState.collect {
                when (it.getInfo) {
                    DetailsContract.DetailsBitsoApiState.Idle -> {}
                    is DetailsContract.DetailsBitsoApiState.Loading -> {}
                    is DetailsContract.DetailsBitsoApiState.Success -> askbidsList = it.getInfo.link
                    is DetailsContract.DetailsBitsoApiState.SuccessTrades -> TradesList = it.getInfo.link
                }
            }
        }

        Column {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("start")
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "null")
                    }
                },
                title = {
                    Text("  ${shortToken(string.toString())}")
                },
            )

            if (askbidsList.isEmpty() || TradesList.isEmpty()) {
                Loadingview("Estamos cargando la información")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2F),
                    rememberLazyListState(),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    itemsIndexed(askbidsList) { _, list ->
                        MoneyDetails(list)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "Monto ", modifier = Modifier.padding(start = 8.dp))
                Text(text = "Tipo de Operación", modifier = Modifier.padding(start = 8.dp))
                Text(text = "Precio C/V")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85F)
                    .padding(16.dp),
            ) {
                if (TradesList.isNotEmpty()) {
                    itemsIndexed(TradesList.take(25)) { _, data ->
                        ItemTrading(list = data)
                    }
                }
            }
        }
    }
}
