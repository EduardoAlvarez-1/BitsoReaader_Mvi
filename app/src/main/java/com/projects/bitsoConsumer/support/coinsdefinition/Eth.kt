package com.projects.bitsoConsumer.support.coinsdefinition

import com.projects.mvi.R

class Eth : Coins() {
    override fun getCoin(): String = "Ethereum"
    override fun getIcon(): Int = R.drawable.cripto_ethereum
    override fun getCoinShorter() = "ETH"
}
