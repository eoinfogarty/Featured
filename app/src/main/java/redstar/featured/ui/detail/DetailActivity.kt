package redstar.featured.ui.detail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import redstar.featured.FeatureApplication
import redstar.featured.R
import redstar.featured.data.dto.Detail
import redstar.featured.databinding.ActivityDetailBinding
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    companion object {
        val KEY_APP_ID = "KEY_APP_ID"
    }

    @Inject lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FeatureApplication.component.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        subscriptions.addAll(
                viewModel
                        .getDetailObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { setDetails(it) }
        )

        val appId = intent.getIntExtra(KEY_APP_ID, 0)
        viewModel.onCreate(appId, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        viewModel.onSaveInstanceState(outState)
    }

    private fun setDetails(details: Detail) {
        binding.toolbarLayout.title = details.name
        binding.description.loadData(details.description, "text/html; charset=utf-8", "UTF-8")

        Glide.with(this)
                .load(details.headerUrl)
                .into(binding.header)
    }

    override fun onDestroy() {
        subscriptions.dispose()
        super.onDestroy()
    }
}
