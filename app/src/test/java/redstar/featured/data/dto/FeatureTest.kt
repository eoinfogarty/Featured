package redstar.featured.data.dto

import org.amshove.kluent.shouldEqualTo
import org.apache.maven.artifact.ant.shaded.IOUtil
import org.junit.Test
import redstar.featured.GsonUtil

class FeatureTest {

    val gson = GsonUtil.gson

    @Test
    fun parsesFeatureJson() {
        val feature = gson.fromJson(
                IOUtil.toString(javaClass.getResourceAsStream("/feature.json")),
                Feature::class.java
        )

        feature.id shouldEqualTo 553280
        feature.title shouldEqualTo "Stellaris: Utopia"
        feature.discounted shouldEqualTo false
        feature.finalPrice shouldEqualTo 198000
        feature.discountPercent shouldEqualTo 0
        feature.headerImage shouldEqualTo "http://cdn.akamai.steamstatic.com/steam/apps/553280/header.jpg?t=1491483378"
    }
}
