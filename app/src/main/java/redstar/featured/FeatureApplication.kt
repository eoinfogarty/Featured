package redstar.featured

import android.app.Application
import redstar.featured.data.ApiComponent
import redstar.featured.data.DaggerApiComponent

class FeatureApplication : Application() {

    companion object {
        lateinit var component : ApiComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApiComponent.builder().build()
    }
}
