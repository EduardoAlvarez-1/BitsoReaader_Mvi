package com.projects.bitsoConsumer.support.coinsdefinition

import com.projects.mvi.R

class Mana : Coins() {
    override fun getCoin(): String = "Mana/Decentraland "
    override fun getIcon(): Int = R.drawable.cripto_mana
    override fun getCoinShorter() = "MANA"
}
