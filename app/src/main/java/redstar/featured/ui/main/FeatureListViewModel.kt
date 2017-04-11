package redstar.featured.ui.main

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import redstar.featured.data.api.SteamClient
import redstar.featured.data.dto.Feature
import redstar.featured.data.dto.FeatureResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureListViewModel @Inject constructor(
        val steamClient: SteamClient
) {
    private val tag = FeatureViewModel::class.java.simpleName

    private val featuresSubject: BehaviorSubject<List<Feature>> = BehaviorSubject.create()
    private val isLoadingSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    fun getFeatured(): Flowable<List<Feature>> {
        if (isLoadingSubject.value) {
            return Flowable.empty()
        }

        isLoadingSubject.onNext(true)

        return steamClient
                .getFeatured()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(FeatureResponse::featured)
                .doOnNext { featuresSubject.onNext(it) }
                .doOnError { Log.e(tag, "Error loading features : $it") }
                .doAfterTerminate { isLoadingSubject.onNext(false) }
    }

    fun getLoadingObservable(): Observable<Boolean> {
        return isLoadingSubject
    }

    fun getFeaturesObservable(): Observable<List<Feature>> {
        return featuresSubject
    }
}
