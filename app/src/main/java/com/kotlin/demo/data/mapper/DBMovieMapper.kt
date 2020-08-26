package com.kotlin.demo.data.mapper

import com.kotlin.demo.data.model.response.Results
import com.kotlin.demo.data.source.local.table.Movie

object DBMovieMapper {
    fun from(results: Results): Movie {
        return Movie(
            results.id,
            results.title,
            results.url
        )
    }
}