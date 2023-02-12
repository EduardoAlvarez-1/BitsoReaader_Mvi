package com.projects.bitsoConsumer.support.coinsdefinition

import com.projects.mvi.R

class Trg : Coins() {
    override fun getCoin(): String = "Tigres Fan Token"
    override fun getIcon(): Int = R.drawable.tigres_cripto
    override fun getCoinShorter() = "Tigres"
}
