package redstar.featured.data.dto

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBe
import org.apache.maven.artifact.ant.shaded.IOUtil
import org.junit.Test
import java.lang.reflect.Type

class DetailTest {

    val gson = createGson()

    @Test
    fun parseDetailJson() {
        val type: Type = TypeToken.get(HashMap<String, DetailResponse>().javaClass).type
        val json: String = IOUtil.toString(javaClass.getResourceAsStream("/detail.json"))

        val response: HashMap<String, DetailResponse> = gson.fromJson(json, type)

        response.values.size shouldEqualTo 1

        val detail = response.values.elementAt(0).data
        detail shouldNotBe null
        detail.name shouldBe "Stellaris: Utopia"
        detail.headerUrl shouldBe "http://cdn.akamai.steamstatic.com/steam/apps/553280/header.jpg?t=1491836203"
        detail.description shouldBe "Build a Better Space Empire in <strong>Stellaris: Utopia</strong><br><br>The stars have called you for millennia and now you walk among them. A universe of possibilities is open to your species as it takes its first fitful steps into the great unknown. Here you can turn your back on the divisive politics of the home planet. Here you can build something new. Here you can unify your people and build that perfect society.<br><br>Only in space can you build Utopia.<br><br>Utopia is the first major expansion for Stellaris, the critically acclaimed science fiction grand strategy game from Paradox Development Studio. As the title suggests, Utopia gives you new tools to develop your galactic empire and keep your people (or birdfolk or talking mushrooms) happy. Push your species further out into the galaxy with new bonuses for rapid exploration or stay closer to home before striking out against all who would challenge you.<br><br><strong>Utopia brings a host of changes to the core of Stellaris, including:</strong><h2 class=\"bb_tag\">Megastructures: </h2>Build wondrous structures in your systems including Dyson Spheres and ring worlds, bringing both prestige and major advantages to your race.<h2 class=\"bb_tag\">Habitat Stations: </h2>Build \u201ctall\u201d and establish space stations that will house more population, serving the role of planets in a small and confined empire.<h2 class=\"bb_tag\">Ascension Perks:</h2> Collect Unity points and adopt Traditions to unlock Ascension Perks that allow you to customize your empire in unique ways. Follow one of the three Ascension Paths and achieve Biological Mastery, give up your biological forms in a Synthetic Evolution, or unlock the full psionic potential of your species through Transcendance.<h2 class=\"bb_tag\">Indoctrination: </h2>Influence primitive civilizations and make them adopt your ethics through the use of observation stations, preparing them for enlightenment or annexation.<h2 class=\"bb_tag\">Advanced Slavery:</h2> Maximize the benefits of slavery by choosing specific roles for enthralled species. Have them serve other Pops as Domestic Servants, fight for your empire as Battle Thralls, or keep them as Livestock to feed your people.<h2 class=\"bb_tag\">Advanced Governments: </h2>Adopt unique civics and authorities for your government. Play as a Fanatic Purifier and shun all diplomacy, become a Hive Mind to avoid political strife or create a multi-species empire born of Syncretic Evolution."
    }

    private fun createGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }
}
