package redstar.featured.data.dto

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.apache.maven.artifact.ant.shaded.IOUtil
import org.junit.Test

class FeatureResponseTest {

    val gson = createGson()

    @Test
    fun parsesFeatureJson() {
        val feature = gson.fromJson(
                IOUtil.toString(javaClass.getResourceAsStream("/featured.json")),
                FeatureResponse::class.java
        )

        feature.featured shouldNotBe null
        feature.featured shouldBeInstanceOf(List::class.java)
        feature.featured.size shouldBe 10
    }

    private fun createGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }
}
