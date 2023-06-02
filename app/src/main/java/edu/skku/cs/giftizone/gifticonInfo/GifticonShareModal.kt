package edu.skku.cs.giftizone.gifticonInfo

import android.content.Context
import android.view.LayoutInflater
import edu.skku.cs.giftizone.R

class GifticonShareModal(
    private val context: Context,
    private val shareId: String,
){
    private val inflater = LayoutInflater.from(context)
    private val dialogLayout = inflater.inflate(R.layout.gifticon_share_modal, null)
    private val shareIdText = dialogLayout.findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.shareIdText)
    private val shareDialog = androidx.appcompat.app.AlertDialog.Builder(context).create()

    init {
        shareDialog.setView(dialogLayout)
        shareIdText.text = shareId
    }

    fun show() {
        shareDialog.show()
    }
}