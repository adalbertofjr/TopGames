package br.com.adalbertofjr.topgames.data.repository

/**
 * RepositoryResponseListerner
 * Created by Adalberto Fernandes Júnior on 14/10/2018.
 * Copyright © 2018. All rights reserved.
 */
interface RepositoryResponseListerner<T> {

    fun onSuccess(response: T)

    fun onFailure(message: String)
}