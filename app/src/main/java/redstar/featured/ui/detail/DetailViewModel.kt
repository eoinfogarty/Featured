package redstar.featured.ui.detail

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

    private val detailSubject: BehaviorSubject<Detail> = BehaviorSubject.create()

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
