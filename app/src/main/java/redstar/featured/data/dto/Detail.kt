package redstar.featured.data.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Detail(

        @SerializedName("name")
        val name: String,

        @SerializedName("header_image")
        val headerUrl: String,

        @SerializedName("detailed_description")
        val description: String

) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Detail> = object : Parcelable.Creator<Detail> {
            override fun createFromParcel(source: Parcel): Detail = Detail(source)
            override fun newArray(size: Int): Array<Detail?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            name = source.readString(),
            headerUrl = source.readString(),
            description = source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(headerUrl)
        dest?.writeString(description)
    }
}
