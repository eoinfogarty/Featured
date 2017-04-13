package redstar.featured.ui.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import redstar.featured.FeatureApplication
import redstar.featured.R
import redstar.featured.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: FeatureListViewModel
    private lateinit var binding: ActivityMainBinding

    private val adapter = FeatureListAdapter()
    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FeatureApplication.component.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        subscriptions.addAll(
                viewModel.getFeaturesObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { adapter.setFeatures(it) },

                viewModel.getLoadingObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { showLoading(it) }

        )

        if (savedInstanceState == null) {
            subscriptions.add(viewModel.getFeatured().subscribe())
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progress.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        subscriptions.dispose()
        super.onDestroy()
    }
}
