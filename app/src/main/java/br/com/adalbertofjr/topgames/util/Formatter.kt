package br.com.adalbertofjr.topgames.util

/**
 * Format
 * Created by Adalberto Fernandes Júnior on 14/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class Formatter {

    fun formatChannelView(channels: Int, viewers: Int): String {
        return "$channels canais - $viewers visualizações"
    }
}