package com.assignm.wikiimagesearch.Api


import com.assignm.wikiimagesearch.model.Query
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    var BASE_URL:String="https://en.wikipedia.org/w/"
    val getClient: ApiService
        get() {
            val percentDeserializer =
                GsonBuilder().registerTypeAdapter(Query::class.java, MyDeserializer()).create()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(percentDeserializer))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(ApiService::class.java)

        }

}