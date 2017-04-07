package redstar.featured

import android.view.View
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import redstar.featured.list.Feature
import redstar.featured.list.FeatureViewModel

@RunWith(RobolectricTestRunner::class)
@Config(
        constants = BuildConfig::class,
        manifest = "src/main/AndroidManifest.xml"
)
class FeatureViewModelTest {

    lateinit var feature: Feature
    lateinit var fullPriceViewModel: FeatureViewModel

    @Before
    fun setup() {
        feature = createMockFullPriceFeature()
        fullPriceViewModel = FeatureViewModel(feature)
    }

    private fun createMockFullPriceFeature(): Feature = Feature(
            title = "Stellaris: Utopia",
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/553280/header.jpg?t=1491483378",
            discounted = false,
            finalPrice = 198000
    )

    private fun createMockDiscountedFeature(): Feature = Feature(
            title = "Crazy Fishing",
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/594370/header.jpg?t=1491472265",
            discounted = true,
            finalPrice = 158400
    )

    private fun createMockFreeFeature(): Feature = Feature(
            title = "Shardbound",
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/586030/header.jpg?t=1491494446",
            discounted = false,
            finalPrice = 0
    )

    @Test
    fun shouldGetName() {
        fullPriceViewModel.getTitle() shouldEqualTo feature.title
    }

    @Test
    fun shouldGetHeaderImage() {
        fullPriceViewModel.getHeaderImage() shouldEqualTo feature.headerImage
    }

    @Test
    fun shouldGetDiscountVisibility() {
        val discountedViewModel = FeatureViewModel(createMockDiscountedFeature())

        fullPriceViewModel.getDiscountVisibility() shouldEqualTo View.GONE
        discountedViewModel.getDiscountVisibility() shouldEqualTo View.VISIBLE
    }

    @Test
    fun shouldGetPrice() {
        val freeViewModel = FeatureViewModel(createMockFreeFeature())

        fullPriceViewModel.getPrice() shouldEqualTo feature.finalPrice.toString()
        freeViewModel.getPrice() shouldEqualTo RuntimeEnvironment.application.getString(R.string.free)
    }
}
