package com.projects.bitsoConsumer.support.coinsdefinition


import com.projects.mvi.R


class Btc: Coins(){
    override fun getCoin(): String ="Bitcoin"
    override fun getIcon(): Int = R.drawable.cripto_bitcoin
    override fun getCoinShorter()="BTC"
}