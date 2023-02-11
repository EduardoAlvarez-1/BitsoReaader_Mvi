package com.projects.bitsoConsumer.support.coinsdefinition


import com.projects.mvi.R


class Bch: Coins(){
    override fun getCoin(): String ="Bitcoin Cash"
    override fun getIcon(): Int = R.drawable.cripto_bhc
    override fun getCoinShorter()="BCH"
}