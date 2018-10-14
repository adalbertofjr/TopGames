package br.com.adalbertofjr.topgames.gameslist.domain.model

import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.data.api.model.Links

/**
 * ListGamesModel
 * Created by Adalberto Fernandes Júnior on 14/10/2018.
 * Copyright © 2018. All rights reserved.
 */
data class TopGames(val games: List<Game>, val links : Links)