package com.projects.bitsoConsumer.support.coinsdefinition

import com.projects.mvi.R

class Eur : Coins() {
    override fun getCoin(): String = "Euro To Mex"
    override fun getIcon(): Int = R.drawable.cripto_default
    override fun getCoinShorter() = "Eur/Mxn"
}
