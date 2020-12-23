package com.assignm.wikiimagesearch.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.assignm.wikiimagesearch.ImageDetail
import com.assignm.wikiimagesearch.R
import com.assignm.wikiimagesearch.model.Pages

import com.squareup.picasso.Picasso


class SearchResultsAdapter(var imagesList:ArrayList<Pages>, var context:Context) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    val tempList= ArrayList<String>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_image_card, parent, false))
    }

    override fun getItemCount(): Int {
      return imagesList.size
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val dataModel=imagesList.get(position)
        val picasso = Picasso.get()
        val url:String?= dataModel.thumbnail?.source
        val finalUrl=url?.substringAfter("\"")?.substringBefore("\"")
        picasso.load(finalUrl).placeholder(context.resources.getDrawable(R.drawable.loading)).fit().centerCrop().into(holder.image_src)
        holder.image_src.setOnClickListener {
            val intent=Intent(context, ImageDetail::class.java)
            intent.putExtra("image_name",imagesList.get(position).title)
            intent.putExtra("image_url",imagesList.get(position).thumbnail.source)
            startActivity(context,intent,null)
        }
    }

    fun clearData():Unit{
        imagesList.clear()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       // var name=itemView.findViewById<TextView>(R.id.image_name)
        var image_src=itemView.findViewById<ImageView>(R.id.search_image)
    }
}