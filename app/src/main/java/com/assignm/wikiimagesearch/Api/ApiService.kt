package com.assignm.wikiimagesearch.Api



import com.assignm.wikiimagesearch.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api.php?action=query&prop=pageimages&format=json&piprop=thumbnail&pilimit=150&generator=prefixsearch")
    fun getResultData(@Query("gpssearch")searchstring:String,@Query("pithumbsize")thumbSize:Int):Call<SearchResponse>
}