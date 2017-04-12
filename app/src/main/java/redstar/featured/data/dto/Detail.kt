package redstar.featured.data.dto

import com.google.gson.annotations.SerializedName

data class Detail(

        @SerializedName("name")
        val name: String,

        @SerializedName("header_image")
        val headerUrl: String,

        @SerializedName("detailed_description")
        val description: String

)
