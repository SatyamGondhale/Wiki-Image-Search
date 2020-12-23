package com.assignm.wikiimagesearch.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchResponse(@SerializedName("query")
                          @Expose
                          var query:Query?)