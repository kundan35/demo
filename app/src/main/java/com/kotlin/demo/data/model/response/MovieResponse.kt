package com.kotlin.demo.data.model.response

import com.google.gson.annotations.SerializedName


data class MovieResponse(
    var page: Int,
    @SerializedName("total_result")
    var totResult: Int,
    @SerializedName("total_pages")
    var totPages: Int,
    @SerializedName("results")
    var results: List<Results>?


)

data class Results(
    var id: Long,
    var title: String,
    @SerializedName("poster_path")
    var url: String?
)
