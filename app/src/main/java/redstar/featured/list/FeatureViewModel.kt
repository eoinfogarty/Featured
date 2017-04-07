package redstar.featured.list

import android.view.View

class FeatureViewModel(val feature: Feature) {

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
        return if (feature.finalPrice == 0) "Free" else feature.finalPrice.toString()
    }
}
