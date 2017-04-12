package redstar.featured.ui.detail

import android.util.Log
import io.reactivex.Flowable
import redstar.featured.data.api.SteamClient
import redstar.featured.data.dto.Detail
import javax.inject.Inject

class DetailViewModel @Inject constructor(
        val steamClient: SteamClient
) {
    private val tag = DetailViewModel::class.java.simpleName

    fun getDetail(): Flowable<Detail> {
        // todo clean
        return steamClient
                .getDetail(553280)
//                .subscribeOn(Schedulers.io())
                .map { it.values.elementAt(0).data }
                .doOnNext { Log.d(tag, "a response " + it.name) }
    }

}
