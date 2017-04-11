package redstar.featured

import android.content.res.Resources
import android.view.View
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import redstar.featured.data.dto.Feature
import redstar.featured.ui.main.FeatureViewModel

@RunWith(RobolectricTestRunner::class)
@Config(
        constants = BuildConfig::class,
        manifest = "src/main/AndroidManifest.xml"
)
class FeatureViewModelTest {

    lateinit var res: Resources

    @Before
    fun setup() {
        res = RuntimeEnvironment.application.resources
    }

    @Test
    fun shouldGetName() {
        val feature = createMockFullPriceFeature()
        val fullPriceViewModel = FeatureViewModel(res, feature)

        fullPriceViewModel.getTitle() shouldEqualTo feature.title
    }

    @Test
    fun shouldGetHeaderImage() {
        val feature = createMockFullPriceFeature()
        val fullPriceViewModel = FeatureViewModel(res, feature)

        fullPriceViewModel.getHeaderImage() shouldEqualTo feature.headerImage
    }

    @Test
    fun shouldGetFormattedDiscountPercent() {
        val feature = createMockDiscountedFeature()
        val discountedViewModel = FeatureViewModel(res, feature)

        discountedViewModel.getDiscountPercent() shouldEqual
                res.getString(R.string.discount_format, feature.discountPercent.toString())
    }

    @Test
    fun shouldGetDiscountVisibility() {
        val fullPriceViewModel = FeatureViewModel(res, createMockFullPriceFeature())
        val discountedViewModel = FeatureViewModel(res, createMockDiscountedFeature())

        fullPriceViewModel.getDiscountVisibility() shouldEqualTo View.GONE
        discountedViewModel.getDiscountVisibility() shouldEqualTo View.VISIBLE
    }

    @Test
    fun shouldGetPrice() {
        val feature = createMockFullPriceFeature()
        val fullPriceViewModel = FeatureViewModel(res, feature)
        val freeViewModel = FeatureViewModel(res, createMockFreeFeature())

        fullPriceViewModel.getPrice() shouldEqualTo feature.finalPrice.toString()
        freeViewModel.getPrice() shouldEqualTo res.getString(R.string.free)
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
