package redstar.featured.ui.main

import android.content.Context
import android.view.View
import android.widget.Toast
import redstar.featured.R
import redstar.featured.data.dto.Feature

class FeatureViewModel(
        val context: Context,
        val feature: Feature
) {

    fun getTitle(): String {
        return feature.title
    }

    fun getHeaderImage(): String {
        return feature.headerImage
    }

    fun getDiscountVisibility(): Int {
        return if (feature.discounted) View.VISIBLE else View.GONE
    }

    fun getPrice(): String {
        if (feature.finalPrice == 0) {
            return context.getString(R.string.free)
        } else {
            return feature.finalPrice.toString()
        }
    }

    fun getDiscountPercent(): String {
        return context.getString(R.string.discount_format, feature.discountPercent.toString())
    }

    fun onClick(view: View) {
        Toast.makeText(context, "Clicked $feature", Toast.LENGTH_LONG).show()
    }
}
