package redstar.featured.list

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class FeatureListViewModel(val steamClient: SteamClient) {

    val isLoadingSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    fun getFeatured(): Maybe<List<Feature>> {
        if (isLoadingSubject.value) {
            return Maybe.empty()
        }

        isLoadingSubject.onNext(true)

        return steamClient
                .getFeatured()
                .map(FeatureResponse::featuredWin)
                .doAfterTerminate { isLoadingSubject.onNext(false) }
    }

    fun getLoadingObservable(): Observable<Boolean> {
        return isLoadingSubject
    }
}
