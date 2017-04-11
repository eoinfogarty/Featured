package redstar.featured.data

import dagger.Module
import dagger.Provides
import redstar.featured.data.api.SteamClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides @Singleton
    fun providesRetrofit(): Retrofit {

        return Retrofit.Builder()
                .baseUrl("http://store.steampowered.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton @Provides
    fun providesSteamClient(retrofit: Retrofit): SteamClient {
        return retrofit.create(SteamClient::class.java)
    }
}
