package redstar.featured.ui.detail

import com.github.salomonbrys.kotson.fromJson
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.apache.maven.artifact.ant.shaded.IOUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import redstar.featured.GsonUtil
import redstar.featured.RxSchedulersOverrideRule
import redstar.featured.data.api.SteamClient
import redstar.featured.data.dto.DetailResponse

class DetailViewModelTest {

    // todo should be in companion object???
    val appId = 553280

    @Rule @JvmField val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    val gson = GsonUtil.gson
    val steamClient: SteamClient = mock()

    @Before
    fun setup() {
        whenever(steamClient.getDetail(appId)).thenReturn(getDetailGson())
    }

    @Test
    fun loadsDetails() {
        val viewModel = DetailViewModel(steamClient)

        val testSubscriber = viewModel.getDetail(appId).test()
        testSubscriber.assertComplete()
        testSubscriber.assertValueCount(1)

        verify(steamClient).getDetail(appId)
    }

    @Test
    fun shouldGetTitle() {
        val viewModel = DetailViewModel(steamClient)
        val testSubscriber = viewModel.getDetailObservable().test()

        val detail = getDetailGson().blockingSingle().values.elementAt(0).data

        viewModel.getDetail(appId)

        // todo
//        testSubscriber.assertValueCount(1)
//        testSubscriber.assertValue(detail)

        verify(steamClient).getDetail(appId)
    }

    @Test
    fun shouldGetHeaderUrl() {

    }

    @Test
    fun shouldGetDescription() {

    }

    private fun getDetailGson(): Flowable<HashMap<String, DetailResponse>> {

        val json: String = IOUtil.toString(javaClass.getResourceAsStream("/detail.json"))
        val response = gson.fromJson<HashMap<String, DetailResponse>>(json)

        return Flowable.just(response)
    }
}
