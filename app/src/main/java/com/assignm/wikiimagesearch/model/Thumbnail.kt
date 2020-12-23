package com.assignm.wikiimagesearch.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Thumbnail(@SerializedName("source")
                     @Expose
                     var source:String)