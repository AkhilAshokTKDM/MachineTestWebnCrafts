package com.akhil.mt_webncrafts.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhil.mt_webncrafts.databinding.ProductItemBinding
import com.akhil.mt_webncrafts.models.data.Contents


class ProductAdapter(
    private val contents: ArrayList<Contents>
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    class ViewHolder(private var itemBinding: ProductItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(content: Contents) {
            itemBinding.ivProductImage.setImageURI(content.productImage)
            itemBinding.txtProductName.text = content.productName
            itemBinding.txtOffer.text =  "Sale " + content.discount
            if(content.actualPrice == content.offerPrice) {
                itemBinding.txtOffer.visibility = View.INVISIBLE
                itemBinding.txtOfferPrice.visibility = View.INVISIBLE
            } else {
                itemBinding.txtOffer.visibility = View.VISIBLE
                itemBinding.txtOfferPrice.visibility = View.VISIBLE
            }
            itemBinding.txtActualPrice.text = content.actualPrice
            itemBinding.txtActualPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemBinding.txtOfferPrice.text = content.offerPrice
            itemBinding.ratingBar.rating = content.productRating?.toFloat() ?: 0f

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return contents.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(contents[position])
    }

}