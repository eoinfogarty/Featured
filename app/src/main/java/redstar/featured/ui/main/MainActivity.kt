package redstar.featured.ui.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import redstar.featured.FeatureApplication
import redstar.featured.R
import redstar.featured.data.dto.Feature
import redstar.featured.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: FeatureListViewModel
    private lateinit var binding: ActivityMainBinding

    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FeatureApplication.component.inject(this@MainActivity)

        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.recycler.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recycler.adapter = FeatureListAdapter()

        subscriptions.addAll(
                viewModel.getFeaturesObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { features -> addFeaturedItems(features) },

                viewModel.getLoadingObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { isLoading -> showLoading(isLoading) },

                viewModel.getFeatured().subscribe()
        )
    }

    private fun addFeaturedItems(features: List<Feature>) {
        Log.wtf("TAG", "Got a list: $features")
    }

    private fun showLoading(loading: Boolean) {
        binding.progress.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        subscriptions.dispose()
        super.onDestroy()
    }
}
