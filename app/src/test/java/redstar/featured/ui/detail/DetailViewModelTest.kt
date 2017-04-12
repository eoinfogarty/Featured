package redstar.featured.ui.detail

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.apache.maven.artifact.ant.shaded.IOUtil
import org.junit.Before
import org.junit.Test
import redstar.featured.data.api.SteamClient
import redstar.featured.data.dto.DetailResponse
import java.lang.reflect.Type

class DetailViewModelTest {

    val gson = createGson()
    val steamClient: SteamClient = mock()

    @Before
    fun setup() {
        whenever(steamClient.getDetail(553280)).thenReturn(getDetailGson())
    }

    @Test
    fun loadsDetails() {
        val viewModel = DetailViewModel(steamClient)

        val testSubscriber = viewModel.getDetail().test()
        testSubscriber.assertComplete()
        testSubscriber.assertValueCount(1)

        verify(steamClient).getDetail(553280)
    }

    fun createGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    private fun getDetailGson(): Flowable<java.util.HashMap<String, DetailResponse>> {

        val type: Type = TypeToken.get(java.util.HashMap<String, DetailResponse>().javaClass).type
        val json: String = IOUtil.toString(javaClass.getResourceAsStream("/detail.json"))

        return Flowable.just(gson.fromJson(json, type))
    }
}
