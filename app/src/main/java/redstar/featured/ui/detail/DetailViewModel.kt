package redstar.featured.ui.detail

import android.os.Bundle
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import redstar.featured.data.api.SteamClient
import redstar.featured.data.dto.Detail
import javax.inject.Inject

class DetailViewModel @Inject constructor(
        private val steamClient: SteamClient
) {

    companion object {
        private val KEY_DETAILS = "KEY_DETAILS"
    }

    private val detailSubject: BehaviorSubject<Detail> = BehaviorSubject.create()

    fun onCreate(appId: Int, savedState: Bundle?) {
        if (savedState != null) {
            restoreInstanceState(savedState)
        } else {
            getDetail(appId).subscribe() // todo add to composite?
        }
    }

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(KEY_DETAILS, detailSubject.value)
    }

    private fun restoreInstanceState(savedState: Bundle) {
        if (savedState.containsKey(KEY_DETAILS)) {
            detailSubject.onNext(savedState.getParcelable(KEY_DETAILS))
        }
    }

    fun getDetail(appId: Int): Flowable<Detail> {
        return steamClient
                .getDetail(appId)
                .subscribeOn(Schedulers.io())
                .map { it.values.elementAt(0).data }
                .doOnNext { detail -> detailSubject.onNext(detail) }
    }

    fun getDetailObservable(): Observable<Detail> {
        return detailSubject
    }
}
