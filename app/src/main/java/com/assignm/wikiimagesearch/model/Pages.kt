package com.assignm.wikiimagesearch.model

import androidx.annotation.Nullable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class Pages(
    @SerializedName("title")
    @Expose
    var title: String?,
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: Thumbnail
)