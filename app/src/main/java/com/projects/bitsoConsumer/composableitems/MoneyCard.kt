package com.projects.bitsoConsumer.composableitems

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projects.bitsoConsumer.models.DetailedPayload
import com.projects.bitsoConsumer.mvi.features.books.BooksViewModel

@Composable
fun MoneyCard(
    viewModel: BooksViewModel,
    list: DetailedPayload,
    navController: NavHostController,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
            //    viewModel.setEvent(event = MainContract.Event.OnNewClick)
//            loggerD(list.payload.book)
                 navController.navigate("details/${list.pair}")
            },
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp)),
            border = BorderStroke(width = 2.dp, Color.Black),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Box(
                    modifier = Modifier
                        .width(63.dp)
                        .height(72.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            modifier = Modifier.padding(4.dp),
                            painter = painterResource(id = list.icon),
                            contentDescription = null,
                        )
                        Text(text = list.shortname, modifier = Modifier.padding(start = 8.dp))
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 40.dp),
                ) {
                    Text(
                        text = "MaximoHistorico  \$ ${list.payload.maximum_price} ",
                        modifier = Modifier.padding(start = 16.dp),
                    )
                    Text(
                        text = "Minimo Historico \$ ${list.payload.minimum_price} ",
                        modifier = Modifier.padding(start = 16.dp, top = 2.dp),
                    )
                }
            }
        }
    }
}
