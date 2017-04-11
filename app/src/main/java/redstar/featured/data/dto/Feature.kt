package redstar.featured.data.dto

import com.google.gson.annotations.SerializedName

data class Feature(

        @SerializedName("name")
        val title: String,

        @SerializedName("header_image")
        val headerImage: String,

        @SerializedName("discounted")
        val discounted: Boolean,

        @SerializedName("final_price")
        val finalPrice: Int,

        @SerializedName("discount_percent")
        val discountPercent: Int
)
