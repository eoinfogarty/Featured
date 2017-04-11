package redstar.featured.ui.main

import android.content.res.Resources
import android.view.View
import redstar.featured.R
import redstar.featured.data.dto.Feature

class FeatureViewModel(
        val resources: Resources,
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
            return resources.getString(R.string.free)
        } else {
            return feature.finalPrice.toString()
        }
    }

    fun getDiscountPercent(): String {
        return resources.getString(R.string.discount_format, feature.discountPercent.toString())
    }
}
