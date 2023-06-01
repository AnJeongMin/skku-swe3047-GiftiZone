package edu.skku.cs.giftizone.dataClass

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate
import java.util.UUID

data class Gifticon (
    val imagePath: String,
    val barcode: String,
    val tag: String,
    val provider: String,
    val content: String,
    val expiredAt: LocalDate,
    val createAt: LocalDate = LocalDate.now(),
    val id: String = UUID.randomUUID().toString()
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        LocalDate.parse(parcel.readString()),
        LocalDate.parse(parcel.readString()),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imagePath)
        parcel.writeString(barcode)
        parcel.writeString(tag)
        parcel.writeString(provider)
        parcel.writeString(content)
        parcel.writeString(expiredAt.toString())
        parcel.writeString(createAt.toString())
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Gifticon> {
        override fun createFromParcel(parcel: Parcel): Gifticon {
            return Gifticon(parcel)
        }

        override fun newArray(size: Int): Array<Gifticon?> {
            return arrayOfNulls(size)
        }
    }
}
