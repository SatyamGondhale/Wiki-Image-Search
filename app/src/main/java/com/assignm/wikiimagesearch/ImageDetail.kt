package com.assignm.wikiimagesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_detail.*

class ImageDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_detail)
        val image:String?=intent.getStringExtra("image_name")
        val image_url:String?=intent.getStringExtra("image_url")
        val finalName=image?.substringAfter("\"")?.substringBefore("\"")
        image_name_detail.text=finalName
        val finalUrl=image_url?.substringAfter("\"")?.substringBefore("\"")
        Picasso.get().load(finalUrl).placeholder(resources.getDrawable(R.drawable.loading)).fit().centerCrop().into(image_url_detail)
    }
}