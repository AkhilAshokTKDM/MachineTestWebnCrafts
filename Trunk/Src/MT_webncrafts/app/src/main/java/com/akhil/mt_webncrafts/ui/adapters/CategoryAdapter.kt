package com.akhil.mt_webncrafts.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akhil.mt_webncrafts.R
import com.akhil.mt_webncrafts.models.data.Contents
import com.facebook.drawee.view.SimpleDraweeView


class CategoryAdapter(private val mList: ArrayList<Contents>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.txt_category_title)
        val imageView: SimpleDraweeView = itemView.findViewById(R.id.sdv_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listData = mList[position]
        holder.title.text = mList[position].title
        holder.imageView.setImageURI(mList[position].imageUrl)
    }
}