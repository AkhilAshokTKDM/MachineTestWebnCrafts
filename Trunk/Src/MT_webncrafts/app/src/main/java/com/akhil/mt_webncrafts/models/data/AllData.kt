package com.akhil.mt_webncrafts.models.data

import com.google.gson.annotations.SerializedName

data class AllData(
    @SerializedName("type"     ) var type     : String?             = null,
    @SerializedName("title"    ) var title    : String?             = null,
    @SerializedName("contents" ) var contents : ArrayList<Contents> = arrayListOf(),
    @SerializedName("id"       ) var id       : String?             = null,
    @SerializedName("image_url") var imageUrl : String?             = null
)

data class Contents(
    @SerializedName("title"          ) var title         : String? = null,
    @SerializedName("image_url"      ) var imageUrl      : String? = null,
    @SerializedName("sku"            ) var sku           : String? = null,
    @SerializedName("product_name"   ) var productName   : String? = null,
    @SerializedName("product_image"  ) var productImage  : String? = null,
    @SerializedName("product_rating" ) var productRating : Int?    = null,
    @SerializedName("actual_price"   ) var actualPrice   : String? = null,
    @SerializedName("offer_price"    ) var offerPrice    : String? = null,
    @SerializedName("discount"       ) var discount      : String? = null

)



