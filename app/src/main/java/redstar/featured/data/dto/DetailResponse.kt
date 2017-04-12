package redstar.featured.data.dto

import com.google.gson.annotations.SerializedName

data class DetailResponse(

        @SerializedName("success")
        val success: Boolean,

        @SerializedName("data")
        val data: Detail
)
