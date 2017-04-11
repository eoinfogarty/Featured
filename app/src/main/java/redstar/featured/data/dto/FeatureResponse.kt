package redstar.featured.data.dto

import com.google.gson.annotations.SerializedName

data class FeatureResponse(

        @SerializedName("featured_win")
        val featured: List<Feature>
)
