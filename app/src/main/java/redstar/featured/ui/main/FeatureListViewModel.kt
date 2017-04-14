package redstar.featured.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import redstar.featured.data.api.SteamClient
import redstar.featured.data.dto.Feature
import redstar.featured.data.dto.FeatureResponse
import javax.inject.Inject

class FeatureListViewModel @Inject constructor(
        val steamClient: SteamClient
) {
    companion object {
        private val TAG = FeatureViewModel::class.java.simpleName
        private val KEY_FEATURED = "KEY_FEATURED"
    }

    private val featuresSubject: BehaviorSubject<ArrayList<Feature>> = BehaviorSubject.create()
    private val isLoadingSubject: BehaviorSubject<Int> = BehaviorSubject.createDefault(View.GONE)

    fun onCreate(savedState: Bundle?) {
        if (savedState != null) {
            restoreInstanceState(savedState)
        } else {
            getFeatured().subscribe() // todo add to composite?
        }
    }

    // todo test bundle
    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(KEY_FEATURED, featuresSubject.value)
    }

    private fun restoreInstanceState(savedState: Bundle) {
        if (savedState.containsKey(KEY_FEATURED)) {
            featuresSubject.onNext(savedState.getParcelableArrayList(KEY_FEATURED))
        }
    }

    fun getFeatured(): Flowable<ArrayList<Feature>> {
        isLoadingSubject.onNext(View.VISIBLE)
        return steamClient
                .getFeatured()
                .subscribeOn(Schedulers.io())
                .map(FeatureResponse::featured)
                .doOnNext { featured -> featuresSubject.onNext(featured) }
                .doOnError { error -> Log.e(TAG, "Error loading features : $error") }
                .doAfterTerminate { isLoadingSubject.onNext(View.GONE) }
    }

    fun getLoadingObservable(): Observable<Int> {
        return isLoadingSubject
    }

    fun getFeaturesObservable(): Observable<ArrayList<Feature>> {
        return featuresSubject
    }
}
