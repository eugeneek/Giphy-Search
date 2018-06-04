package com.parapaparam.chiphy.data

import com.parapaparam.chiphy.network.GiphyService
import javax.inject.Inject


class GiphyRepository @Inject constructor(
        private val giphyService: GiphyService
) {
    fun search(condition: String, limit: Int, offset: Int) =
            giphyService.search(condition, limit, offset)
}