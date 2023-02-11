package com.projects.bitsoConsumer.support.coinsdefinition

import com.projects.mvi.R

class Tusd: Coins(){
    override fun getCoin(): String ="True USD"
    override fun getIcon(): Int = R.drawable.cripto_tusd
    override fun getCoinShorter()="TUSD"
}