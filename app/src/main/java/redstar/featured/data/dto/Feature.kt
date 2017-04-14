package redstar.featured.data.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Feature(

        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val title: String,

        @SerializedName("header_image")
        val headerImage: String,

        @SerializedName("discounted")
        val discounted: Boolean,

        @SerializedName("final_price")
        val finalPrice: Int,

        @SerializedName("discount_percent")
        val discountPercent: Int

) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Feature> = object : Parcelable.Creator<Feature> {
            override fun createFromParcel(source: Parcel): Feature = Feature(source)
            override fun newArray(size: Int): Array<Feature?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            id = source.readInt(),
            title = source.readString(),
            headerImage = source.readString(),
            discounted = 1.equals(source.readInt()),
            finalPrice = source.readInt(),
            discountPercent = source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(title)
        dest?.writeString(headerImage)
        dest?.writeInt((if (discounted) 1 else 0))
        dest?.writeInt(finalPrice)
        dest?.writeInt(discountPercent)
    }
}
