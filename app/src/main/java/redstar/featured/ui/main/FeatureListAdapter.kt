package redstar.featured.ui.main

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import redstar.featured.R
import redstar.featured.data.dto.Feature
import redstar.featured.databinding.ItemFeatureBinding

class FeatureListAdapter : RecyclerView.Adapter<FeatureListAdapter.ViewHolder>() {

    val features = mutableListOf<Feature>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_feature, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.viewModel = FeatureViewModel(holder.itemView.context, features[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return features.size
    }

    fun setFeatures(data: List<Feature>) {
        features.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemFeatureBinding = DataBindingUtil.bind(itemView)
    }
}
