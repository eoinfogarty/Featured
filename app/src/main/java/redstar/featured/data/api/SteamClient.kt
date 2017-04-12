package redstar.featured.data.api

import io.reactivex.Flowable
import redstar.featured.data.dto.DetailResponse
import redstar.featured.data.dto.FeatureResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamClient {
    @GET("featured")
    fun getFeatured(): Flowable<FeatureResponse>

    @GET("appdetails")
    fun getDetail(@Query("appids") appid: Int): Flowable<HashMap<String, DetailResponse>>
}

