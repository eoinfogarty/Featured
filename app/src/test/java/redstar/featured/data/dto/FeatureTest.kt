package redstar.featured.data.dto

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.amshove.kluent.shouldEqualTo
import org.apache.maven.artifact.ant.shaded.IOUtil
import org.junit.Test
import redstar.featured.data.dto.Feature

class FeatureTest {

    val gson = createGson()

    @Test
    fun parsesFeatureJson() {
        val feature = gson.fromJson(
                IOUtil.toString(javaClass.getResourceAsStream("/feature.json")),
                Feature::class.java
        )

        feature.title shouldEqualTo "Stellaris: Utopia"
        feature.discounted shouldEqualTo false
        feature.finalPrice shouldEqualTo 198000
        feature.discountPercent shouldEqualTo 0
        feature.headerImage shouldEqualTo "http://cdn.akamai.steamstatic.com/steam/apps/553280/header.jpg?t=1491483378"
    }

    private fun createGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }
}
