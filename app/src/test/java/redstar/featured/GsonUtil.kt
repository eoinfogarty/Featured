package redstar.featured

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonUtil {
    val gson = createGson()

    private fun createGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }
}
