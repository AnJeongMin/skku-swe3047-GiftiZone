package edu.skku.cs.giftizone.gifticonList

import android.content.Context
import android.view.LayoutInflater
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.dataClass.Gifticon

class GetGifticonModal(
    private val context: Context,
    private val addGifticonHandler: (Gifticon) -> Unit,
) {
    private val inflater = LayoutInflater.from(context)
    private val dialogLayout = inflater.inflate(R.layout.get_gifticon_modal, null)
    private val getGifticonDialog = androidx.appcompat.app.AlertDialog.Builder(context).create()

    init {
        getGifticonDialog.setView(dialogLayout)
    }

    fun show() {
        getGifticonDialog.show()
    }
}