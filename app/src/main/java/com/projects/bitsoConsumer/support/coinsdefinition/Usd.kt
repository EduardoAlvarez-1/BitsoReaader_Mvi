package com.projects.bitsoConsumer.support.coinsdefinition

import com.projects.mvi.R

class Usd : Coins() {
    override fun getCoin(): String = "USD"
    override fun getIcon(): Int = R.drawable.cripto_usd
    override fun getCoinShorter() = "USD"
}
