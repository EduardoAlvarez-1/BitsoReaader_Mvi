package com.projects.bitsoConsumer.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projects.bitsoConsumer.composableitems.canvas.QuadLineChart
import com.projects.bitsoConsumer.models.trading.PayloadTrades
import com.projects.bitsoConsumer.models.trading.PayloadTradesGraph
import com.projects.bitsoConsumer.mvi.features.tradeChart.ChartContract
import com.projects.bitsoConsumer.mvi.features.tradeChart.TradingChartViewModel
import com.projects.bitsoConsumer.support.TokenName
import com.projects.mvi.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun TradingChart(viewModel: TradingChartViewModel, navController: NavHostController, string: String?) {
    val scope = rememberCoroutineScope()
    val datas = 25
    val DarkBlue = Color(0xFF060D2E)
    val TextWhite = Color(0xFFEEEEEE)

    var tradesList: List<PayloadTrades> by remember {
        mutableStateOf(emptyList())
    }

    // filtros
    var ListaCompras: List<PayloadTrades> by remember {
        mutableStateOf(emptyList())
    }

    var ListaVentas: List<PayloadTrades> by remember {
        mutableStateOf(emptyList())
    }
    //

    // savers
    val newList = mutableListOf<PayloadTradesGraph>()
    val newList2 = mutableListOf<PayloadTradesGraph>()
    //

    // rememberable
    var listaVentasN: List<PayloadTradesGraph> by
        rememberSaveable {
            mutableStateOf(emptyList())
        }

    var listaComprasN: List<PayloadTradesGraph> by
        rememberSaveable {
            mutableStateOf(emptyList())
        }

    var refresh by remember {
        mutableStateOf(true)
    }

    var download by remember {
        mutableStateOf(true)
    }

    with(viewModel) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(1.seconds)
                refresh = false
                delay(3.seconds)
                refresh = true
                delay(1.seconds)
                refresh = false

                string?.let {
                    setEvent(ChartContract.Event.OnUpdate(it))
                }
            }
        }

        LaunchedEffect(scope) {
            viewModel.uiState.collect {
                when (it.getInfo) {
                    ChartContract.ChartBitsoApiState.Idle -> {}
                    is ChartContract.ChartBitsoApiState.Flags -> {
                        download = it.getInfo.downloading
                        refresh = it.getInfo.Update
                    }
                    is ChartContract.ChartBitsoApiState.SuccessTrades ->
                        {
                            tradesList = it.getInfo.link
                        }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue),
        ) {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("details/$string")
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "null")
                    }
                },
                actions = {
                    if (download) {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_download_24),
                                contentDescription = "",
                            )
                        }
                    }
                    if (refresh) {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.round_autorenew_24),
                                contentDescription = "",
                            )
                        }
                    }
                },
                title = {
                    Row {
                        Text(" ${TokenName(string.toString())}  ")
                        Text(text = "C", color = Color.Red)
                        Text(text = "/", color = Color.White)
                        Text(text = "V", color = Color.Green)
                    }
                },
            )

            ListaCompras = tradesList.filter {
                it.maker_side == "Compra"
            }
            ListaVentas = tradesList.filter {
                it.maker_side == "Venta"
            }

            ListaCompras.forEachIndexed { index, it ->
                val value = it.price.toDouble()
                newList.add(PayloadTradesGraph(index, value))
            }

            ListaVentas.forEachIndexed { index, it ->
                val value = it.price.toDouble()
                newList2.add(PayloadTradesGraph(index, price = value))
            }

            listaVentasN = newList
            listaComprasN = newList2

            if (listaVentasN.isEmpty() || listaComprasN.isEmpty()) {
                download = true
                Loadingview("Estamos generando el gráfico ")
            } else {
                download = false
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5F)
                        .align(Alignment.CenterHorizontally),

                ) {
                    QuadLineChart(
                        data = if (listaComprasN.size > datas) listaComprasN.take(datas) else listaComprasN,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.75F)
                            .align(Alignment.BottomStart),
                        color1 = Color.Red,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .align(Alignment.CenterHorizontally),

                ) {
                    QuadLineChart(
                        data = if (listaVentasN.size > datas) listaVentasN.take(datas) else listaVentasN,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.75F)
                            .align(Alignment.BottomStart),

                        color1 = Color.Green,
                    )
                }
            }
        }
    }
}
