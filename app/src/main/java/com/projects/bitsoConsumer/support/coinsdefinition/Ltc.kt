package com.projects.bitsoConsumer.support.coinsdefinition

import com.projects.mvi.R

class Ltc : Coins() {
    override fun getCoin(): String = "Little Coin"
    override fun getIcon(): Int = R.drawable.cripto_ltc
    override fun getCoinShorter() = "LTC"
}
