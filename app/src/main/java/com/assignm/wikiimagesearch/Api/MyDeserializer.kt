package com.assignm.wikiimagesearch.Api

import android.util.Log
import com.assignm.wikiimagesearch.model.Pages
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import com.assignm.wikiimagesearch.model.Query
import com.assignm.wikiimagesearch.model.Thumbnail

class MyDeserializer :JsonDeserializer<Query> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Query? {
        var pagesList = ArrayList<Pages>()
    //    val jsonElement:JsonElement=json!!.asJsonObject["pages"]
        val content = json?.asJsonObject?.get("pages")
        val iter:Iterator<String>
        iter=content?.asJsonObject?.keySet()!!.iterator()
        while (iter.hasNext()){
            val key:String=iter.next()
            val obj:JsonObject= content.asJsonObject[key] as JsonObject
            val title:String=obj.get("title").toString()
            val isKeyPresent:Boolean=obj.has("thumbnail")
            if(isKeyPresent){
                val thumbnail:JsonObject?= obj.asJsonObject["thumbnail"] as JsonObject
                val source:String=thumbnail?.get("source").toString()
                val thumbNail: Thumbnail = Thumbnail(source)
                val page:Pages=Pages(title,thumbNail)
                pagesList.add(page)
            }
        }
        val s:Query=Query(pagesList)
        return s
    }
}