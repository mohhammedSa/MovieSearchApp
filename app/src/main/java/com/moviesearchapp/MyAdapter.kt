package com.moviesearchapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class MyAdapter(val context: Context, val layoutRes: Int, val list: ArrayList<MoviesDataClass>) :
    RecyclerView.Adapter<MyAdapter.MyViewHold>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHold {
        return MyViewHold(LayoutInflater.from(context).inflate(layoutRes, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHold(itemView: View) : ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleView: TextView = itemView.findViewById(R.id.titleTV)
        val overviewTV: TextView = itemView.findViewById(R.id.overviewTV)
        val languageTV: TextView = itemView.findViewById(R.id.languageTV)
        val dateTv: TextView = itemView.findViewById(R.id.dateTV)
    }

    override fun onBindViewHolder(holder: MyViewHold, position: Int) {
        val items = list[position]
        holder.titleView.text = items.title
        holder.overviewTV.text = items.overview
        holder.languageTV.text = items.lang
        holder.dateTv.text = items.date
        val imageUrl = "https://image.tmdb.org/t/p/w500${items.img}"
        Picasso.get().load(imageUrl).into(holder.imageView)
    }
}