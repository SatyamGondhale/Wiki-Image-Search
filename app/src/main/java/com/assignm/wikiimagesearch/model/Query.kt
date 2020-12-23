package com.assignm.wikiimagesearch.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Query(@SerializedName("query")
            @Expose
            var  pages:ArrayList<Pages>)