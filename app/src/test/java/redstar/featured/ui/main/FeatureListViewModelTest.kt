package redstar.featured.ui.main

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.amshove.kluent.mock
import org.apache.maven.artifact.ant.shaded.IOUtil
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import redstar.featured.data.api.SteamClient
import redstar.featured.data.dto.FeatureResponse

@RunWith(MockitoJUnitRunner::class)
class FeatureListViewModelTest {

    val steamClient: SteamClient = mock()
    val gson: Gson = createGson()

    @Before
    fun setup() {
        whenever(steamClient.getFeatured()).thenReturn(getFeaturedJson())
    }

    @Test
    fun loadsFeatures() {
        val viewModel = FeatureListViewModel(steamClient)

        val testSubscriber = viewModel.getFeatured().test()
        testSubscriber.assertComplete()
        testSubscriber.assertValueCount(1)

        verify(steamClient).getFeatured()
    }

    @Test
    fun togglesLoadingState() {
        val viewModel = FeatureListViewModel(steamClient)

        // initial state is not loading
        var testSubscriber = viewModel.getLoadingObservable().test()
        testSubscriber.assertValue(false)

        // start loading
        val loadFeatured = viewModel.getFeatured()
        testSubscriber = viewModel.getLoadingObservable().test()
        testSubscriber.assertValue(true)

        // finish loading
        loadFeatured.subscribe()
        testSubscriber = viewModel.getLoadingObservable().test()
        testSubscriber.assertValue(false)
    }

    fun createGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    fun getFeaturedJson(): Flowable<FeatureResponse> {
        val response = gson.fromJson(
                IOUtil.toString(javaClass.getResourceAsStream("/featured.json")),
                FeatureResponse::class.java
        )

        return Flowable.just(response)
    }
}
