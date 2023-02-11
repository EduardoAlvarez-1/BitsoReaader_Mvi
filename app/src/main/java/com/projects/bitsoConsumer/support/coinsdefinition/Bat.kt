package com.projects.bitsoConsumer.support.coinsdefinition


import com.projects.mvi.R


class Bat: Coins(){
    override fun getCoin(): String ="Basic Attention Token"
    override fun getIcon(): Int = R.drawable.cripto_bat
    override fun getCoinShorter()="BAT"
}