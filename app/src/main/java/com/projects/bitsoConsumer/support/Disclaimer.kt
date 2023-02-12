package com.projects.bitsoConsumer.support

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.projects.mvi.R

@Composable
fun Disclaimer() {
    Text(
        text = stringResource(R.string.dis1),
        color = Color.LightGray,
    )
    Text(
        text = stringResource(R.string.dis2),
        color = Color.LightGray,
    )
}
