package redstar.featured.ui.main

import android.content.Context
import android.view.View
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import redstar.featured.BuildConfig
import redstar.featured.R
import redstar.featured.data.dto.Feature

@RunWith(RobolectricTestRunner::class)
@Config(
        constants = BuildConfig::class,
        manifest = "src/main/AndroidManifest.xml"
)
class FeatureViewModelTest {

    lateinit var context: Context

    @Before
    fun setup() {
        context = RuntimeEnvironment.application
    }

    @Test
    fun shouldGetName() {
        val feature = createMockFullPriceFeature()
        val fullPriceViewModel = FeatureViewModel(context, feature)

        fullPriceViewModel.getTitle() shouldEqualTo feature.title
    }

    @Test
    fun shouldGetHeaderImage() {
        val feature = createMockFullPriceFeature()
        val fullPriceViewModel = FeatureViewModel(context, feature)

        fullPriceViewModel.getHeaderImage() shouldEqualTo feature.headerImage
    }

    @Test
    fun shouldGetFormattedDiscountPercent() {
        val feature = createMockDiscountedFeature()
        val discountedViewModel = FeatureViewModel(context, feature)

        discountedViewModel.getDiscountPercent() shouldEqual
                context.getString(R.string.discount_format, feature.discountPercent.toString())
    }

    @Test
    fun shouldGetDiscountVisibility() {
        val fullPriceViewModel = FeatureViewModel(context, createMockFullPriceFeature())
        val discountedViewModel = FeatureViewModel(context, createMockDiscountedFeature())

        fullPriceViewModel.getDiscountVisibility() shouldEqualTo View.GONE
        discountedViewModel.getDiscountVisibility() shouldEqualTo View.VISIBLE
    }

    @Test
    fun shouldGetPrice() {
        val feature = createMockFullPriceFeature()
        val fullPriceViewModel = FeatureViewModel(context, feature)
        val freeViewModel = FeatureViewModel(context, createMockFreeFeature())

        fullPriceViewModel.getPrice() shouldEqualTo feature.finalPrice.toString()
        freeViewModel.getPrice() shouldEqualTo context.getString(R.string.free)
    }

    private fun createMockFullPriceFeature(): Feature = Feature(
            title = "Stellaris: Utopia",
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/553280/header.jpg?t=1491483378",
            discounted = false,
            finalPrice = 198000,
            discountPercent = 0
    )

    private fun createMockDiscountedFeature(): Feature = Feature(
            title = "Crazy Fishing",
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/594370/header.jpg?t=1491472265",
            discounted = true,
            finalPrice = 158400,
            discountPercent = 0
    )

    private fun createMockFreeFeature(): Feature = Feature(
            title = "Shardbound",
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/586030/header.jpg?t=1491494446",
            discounted = false,
            finalPrice = 0,
            discountPercent = 0
    )
}
