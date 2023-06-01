package edu.skku.cs.giftizone.gifticonInfo

import android.content.Context
import android.view.LayoutInflater
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.dataClass.Gifticon
import edu.skku.cs.giftizone.gifticonList.TagMenuAdapter

class GifticonShareModal(
    private val context: Context,
    private val gifticon: Gifticon,
){
    private val inflater = LayoutInflater.from(context)
    private val dialogLayout = inflater.inflate(R.layout.gifticon_share_modal, null)
    private val shareDialog = androidx.appcompat.app.AlertDialog.Builder(context).create()

    init {
        shareDialog.setView(dialogLayout)
    }

    fun show() {
        shareDialog.show()
    }
}