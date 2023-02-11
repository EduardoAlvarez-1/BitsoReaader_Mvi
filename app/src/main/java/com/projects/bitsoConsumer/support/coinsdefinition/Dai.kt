package com.projects.bitsoConsumer.support.coinsdefinition

import com.projects.mvi.R

class Dai : Coins() {
    override fun getCoin(): String = "DAI"
    override fun getIcon(): Int = R.drawable.cripto_dai
    override fun getCoinShorter() = "DAI"
}
