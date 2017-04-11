package redstar.featured.data.api

import io.reactivex.Flowable
import redstar.featured.data.dto.FeatureResponse
import retrofit2.http.GET

interface SteamClient {
    @GET("featured")
    fun getFeatured(): Flowable<FeatureResponse>
}
