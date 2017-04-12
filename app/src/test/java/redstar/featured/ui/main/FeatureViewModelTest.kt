package redstar.featured.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import redstar.featured.BuildConfig
import redstar.featured.FeatureApplication
import redstar.featured.R
import redstar.featured.data.dto.Feature
import redstar.featured.ui.detail.DetailActivity

@RunWith(RobolectricTestRunner::class)
@Config(
        constants = BuildConfig::class,
        sdk = intArrayOf(21)
)
class FeatureViewModelTest {

    @Mock lateinit var context: Context

    lateinit var application: FeatureApplication

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        application = RuntimeEnvironment.application as FeatureApplication
    }

    @Test
    fun shouldGetName() {
        val feature = createMockFullPriceFeature()
        val fullPriceViewModel = FeatureViewModel(application, feature)

        fullPriceViewModel.getTitle() shouldEqualTo feature.title
    }

    @Test
    fun shouldGetHeaderImage() {
        val feature = createMockFullPriceFeature()
        val fullPriceViewModel = FeatureViewModel(application, feature)

        fullPriceViewModel.getHeaderImage() shouldEqualTo feature.headerImage
    }

    @Test
    fun shouldGetFormattedDiscountPercent() {
        val feature = createMockDiscountedFeature()
        val discountedViewModel = FeatureViewModel(application, feature)

        discountedViewModel.getDiscountPercent() shouldEqual
                application.getString(R.string.discount_format, feature.discountPercent.toString())
    }

    @Test
    fun shouldShowDiscountLabel() {
        val discountedViewModel = FeatureViewModel(application, createMockDiscountedFeature())

        discountedViewModel.getDiscountVisibility() shouldEqualTo View.VISIBLE
    }

    @Test
    fun shouldNotShowDiscountLabel() {
        val fullPriceViewModel = FeatureViewModel(application, createMockFullPriceFeature())

        fullPriceViewModel.getDiscountVisibility() shouldEqualTo View.GONE
    }

    @Test
    fun shouldGetPriceWhenNotFree() {
        val feature = createMockFullPriceFeature()
        val fullPriceViewModel = FeatureViewModel(application, feature)

        fullPriceViewModel.getPrice() shouldEqualTo feature.finalPrice.toString()
    }

    @Test
    fun shouldNotGetPriceWhenFree() {
        val freeViewModel = FeatureViewModel(application, createMockFreeFeature())

        freeViewModel.getPrice() shouldEqualTo application.getString(R.string.free)
    }

    @Test
    fun shouldLaunchDetailActivity() {
        val feature = createMockFullPriceFeature()
        val viewModel = FeatureViewModel(context, feature)

        viewModel.onClick(View(application))
        verify(context).startActivity(Intent(context, DetailActivity::class.java))
    }

    private fun createMockFullPriceFeature(): Feature = Feature(
            id = 553280,
            title = "Stellaris: Utopia",
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/553280/header.jpg?t=1491483378",
            discounted = false,
            finalPrice = 198000,
            discountPercent = 0
    )

    private fun createMockDiscountedFeature(): Feature = Feature(
            id = 594370,
            title = "Crazy Fishing",
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/594370/header.jpg?t=1491472265",
            discounted = true,
            finalPrice = 158400,
            discountPercent = 0
    )

    private fun createMockFreeFeature(): Feature = Feature(
            id = 586030,
            title = "Shardbound",
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/586030/header.jpg?t=1491494446",
            discounted = false,
            finalPrice = 0,
            discountPercent = 0
    )
}
