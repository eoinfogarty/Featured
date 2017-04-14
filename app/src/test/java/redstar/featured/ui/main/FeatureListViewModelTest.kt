package redstar.featured.ui.main

import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.amshove.kluent.mock
import org.amshove.kluent.shouldEqual
import org.apache.maven.artifact.ant.shaded.IOUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import redstar.featured.BuildConfig
import redstar.featured.GsonUtil
import redstar.featured.RxSchedulersOverrideRule
import redstar.featured.data.api.SteamClient
import redstar.featured.data.dto.FeatureResponse

@RunWith(RobolectricTestRunner::class)
@Config(
        constants = BuildConfig::class,
        sdk = intArrayOf(21)
)
class FeatureListViewModelTest {

    companion object {
        private val KEY_FEATURED = "KEY_FEATURED"
    }

    @Rule @JvmField val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    val steamClient: SteamClient = mock()
    val gson: Gson = GsonUtil.gson

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
        testSubscriber.assertValue(View.GONE)

        // start loading
        val loadFeatured = viewModel.getFeatured()
        testSubscriber = viewModel.getLoadingObservable().test()
        testSubscriber.assertValue(View.VISIBLE)

        // finish loading
        loadFeatured.subscribe()
        testSubscriber = viewModel.getLoadingObservable().test()
        testSubscriber.assertValue(View.GONE)
    }

    @Test
    fun savesInstanceState() {
        val viewModel = FeatureListViewModel(steamClient)
        val bundle: Bundle = Bundle()

        // load data
        viewModel.getFeatured().subscribe()

        // save data eg on rotation
        viewModel.onSaveInstanceState(bundle)

        bundle.containsKey(KEY_FEATURED) shouldEqual true
        getFeaturedJson().blockingSingle().featured shouldEqual
                bundle.getParcelableArrayList(KEY_FEATURED)
    }

    @Test
    fun restoreInstanceState() {
        val viewModel = FeatureListViewModel(steamClient)
        val bundle: Bundle = Bundle()

        // load data
        viewModel.getFeatured().subscribe()

        // save data eg on rotation
        viewModel.onSaveInstanceState(bundle)

        // restore state
        viewModel.onCreate(bundle)

        viewModel.getFeaturesObservable().blockingFirst() shouldEqual
                getFeaturedJson().blockingFirst().featured
    }

    fun getFeaturedJson(): Flowable<FeatureResponse> {
        val response = gson.fromJson(
                IOUtil.toString(javaClass.getResourceAsStream("/featured.json")),
                FeatureResponse::class.java
        )

        return Flowable.just(response)
    }
}
