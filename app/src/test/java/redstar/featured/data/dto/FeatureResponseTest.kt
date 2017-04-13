package redstar.featured.data.dto

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.apache.maven.artifact.ant.shaded.IOUtil
import org.junit.Test
import redstar.featured.GsonUtil

class FeatureResponseTest {

    val gson = GsonUtil.gson

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
}
