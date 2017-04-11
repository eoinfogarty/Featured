package redstar.featured.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import redstar.featured.R

class FeatureListAdapter : RecyclerView.Adapter<FeatureListAdapter.ViewHolder>() {

    val featured: List<FeatureViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_feature,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

    }

    override fun getItemCount(): Int {
        return featured.size
    }


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }
}
