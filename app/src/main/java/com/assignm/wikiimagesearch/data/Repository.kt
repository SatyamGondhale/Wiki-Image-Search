package com.assignm.wikiimagesearch.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.assignm.wikiimagesearch.Api.Api
import com.assignm.wikiimagesearch.Api.ApiService
import com.assignm.wikiimagesearch.model.Pages
import com.assignm.wikiimagesearch.model.Query
import com.assignm.wikiimagesearch.model.SearchResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository{
    val repoThumbNailDataList=MutableLiveData<ArrayList<Pages>>()
    var apiService: ApiService? = null
    init {
        apiService= Api.getClient
       // getDataFromApi("Apple")
    }

    fun getDataFromApi(search_query: String,thumb_size:Int): MutableLiveData<ArrayList<Pages>> {
        val apiCall = apiService?.getResultData( search_query,thumb_size)
        apiCall?.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                val page: SearchResponse?=response.body()
                val query: Query?= page?.query
                val pagesList: ArrayList<Pages>?=query?.pages
                repoThumbNailDataList.value=pagesList
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.i("GETTING_ERROR",t.message.toString())


            }
        })
         return repoThumbNailDataList
    }
}