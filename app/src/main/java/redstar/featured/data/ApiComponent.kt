package redstar.featured.data

import dagger.Component
import redstar.featured.ui.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApiModule::class
))
interface ApiComponent {

    fun inject(mainActivity: MainActivity)

}
