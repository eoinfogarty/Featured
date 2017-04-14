package redstar.featured.ui.detail

import android.os.Bundle
import com.github.salomonbrys.kotson.fromJson
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
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
import redstar.featured.data.dto.DetailResponse

@RunWith(RobolectricTestRunner::class)
@Config(
        constants = BuildConfig::class,
        sdk = intArrayOf(21)
)
class DetailViewModelTest {

    companion object {
        private val KEY_DETAILS = "KEY_DETAILS"
        private val APP_ID = 553280
    }

    @Rule @JvmField val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    val gson = GsonUtil.gson
    val steamClient: SteamClient = mock()

    @Before
    fun setup() {
        whenever(steamClient.getDetail(APP_ID)).thenReturn(getDetailGson())
    }

    @Test
    fun loadsDetails() {
        val viewModel = DetailViewModel(steamClient)

        val testSubscriber = viewModel.getDetail(APP_ID).test()
        testSubscriber.assertComplete()
        testSubscriber.assertValueCount(1)

        verify(steamClient).getDetail(APP_ID)
    }

    @Test
    fun shouldGetTitle() {
        val viewModel = DetailViewModel(steamClient)
        val testSubscriber = viewModel.getDetailObservable().test()

        val detail = getDetailGson().blockingSingle().values.elementAt(0).data

        viewModel.getDetail(APP_ID)

        // todo
//        testSubscriber.assertValueCount(1)
//        testSubscriber.assertValue(detail)

        verify(steamClient).getDetail(APP_ID)
    }

    @Test
    fun shouldGetHeaderUrl() {

    }

    @Test
    fun shouldGetDescription() {

    }

    @Test
    fun saveInstanceState() {
        val viewModel = DetailViewModel(steamClient)
        val bundle: Bundle = Bundle()

        // load data
        viewModel.getDetail(APP_ID).subscribe()

        // save data eg on rotation
        viewModel.onSaveInstanceState(bundle)

        bundle.containsKey(KEY_DETAILS) shouldEqual true
        getDetailGson().blockingSingle().values.elementAt(0).data shouldEqual
                bundle.getParcelable(KEY_DETAILS)
    }

    @Test
    fun restoreInstanceState() {
        val viewModel = DetailViewModel(steamClient)
        val bundle: Bundle = Bundle()

        // load data
        viewModel.getDetail(APP_ID).subscribe()

        // save data eg on rotation
        viewModel.onSaveInstanceState(bundle)

        // restore the state
        viewModel.onCreate(APP_ID, bundle)

        // todo nice to check api is not called but if i test base to make surerestore is always called should be ok

        // and restored
        viewModel.getDetailObservable().blockingFirst() shouldEqual
                getDetailGson().blockingSingle().values.elementAt(0).data
    }

    private fun getDetailGson(): Flowable<HashMap<String, DetailResponse>> {
        val json: String = IOUtil.toString(javaClass.getResourceAsStream("/detail.json"))
        val response = gson.fromJson<HashMap<String, DetailResponse>>(json)

        return Flowable.just(response)
    }
}
