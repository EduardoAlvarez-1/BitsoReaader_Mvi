package com.projects.bitsoConsumer.support.coinsdefinition

import com.projects.mvi.R

class GenericCoin : Coins() {
    override fun getCoin(): String = "Generic"
    override fun getIcon(): Int = R.drawable.cripto_default
    override fun getCoinShorter() = "Def"
}
