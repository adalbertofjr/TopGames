package br.com.adalbertofjr.topgames.util

import br.com.adalbertofjr.topgames.data.api.model.Box
import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.data.api.model.Logo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * FormatterTest
 * Created by Adalberto Fernandes Júnior on 14/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class FormatterTest {

    @Test
    fun should_ReturnChannelAndViewerFormatted_WhenReceiveAChannelAndViewer() {
        val game = Game(
                "Fortnite",
                Box("https://static-cdn.jtvnw.net/ttv-boxart/Fortnite-272x380.jpg"),
                Logo("https://static-cdn.jtvnw.net/ttv-boxart/Fortnite-272x380.jpg"),
                2345,
                5432)

        val textoFormatadoDevolvido = Formatter().formatChannelView(game.channels, game.viewers)

        assertThat(textoFormatadoDevolvido, `is`(equalTo<String>("2345 canais - 5432 visualizações")))
    }
}