package redstar.featured.list

import io.reactivex.Maybe
import retrofit2.http.GET

interface SteamClient {
    @GET("/featured")
    fun getFeatured(): Maybe<FeatureResponse>
}
