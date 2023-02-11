package com.projects.bitsoConsumer.support.coinsdefinition

import com.projects.mvi.R

class Xrp : Coins() {
    override fun getCoin(): String = "XRP/Ripple"
    override fun getIcon(): Int = R.drawable.cripto_xrp
    override fun getCoinShorter() = "XRP"
}
