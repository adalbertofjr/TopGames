package br.com.adalbertofjr.topgames.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * TwitchData
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */

data class TwitchData(@SerializedName("_total") val total: Int, val top: List<Top>)

data class Top(val channels: Int, val viewers: Int, val game: Game)

data class Game(val name: String, val box: Box, val logo: Logo)

data class Box(val medium: String)

data class Logo(val template: String)