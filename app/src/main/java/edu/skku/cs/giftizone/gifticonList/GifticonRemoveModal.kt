package edu.skku.cs.giftizone.gifticonList

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.dataClass.Gifticon

class GifticonRemoveModal(
    private val context: Context,
    private val gifticon: Gifticon,
    private val removeGifticonHandler: () -> Unit,
) {
    private val inflater = LayoutInflater.from(context)
    private val dialogLayout = inflater.inflate(R.layout.gifticon_remove_modal, null)
    private val cancelRemoveBtn = dialogLayout.findViewById<Button>(R.id.removeGifticonCancelButton)
    private val confirmRemoveBtn = dialogLayout.findViewById<Button>(R.id.removeGifticonConfirmButton)
    private val removeGifticonDialog = androidx.appcompat.app.AlertDialog.Builder(context).create()

    init {
        removeGifticonDialog.setView(dialogLayout)
        confirmRemoveBtn.setOnClickListener {
            removeGifticonHandler()
            removeGifticonDialog.dismiss()
        }
        cancelRemoveBtn.setOnClickListener {
            removeGifticonDialog.cancel()
        }
    }

    fun show() {
        removeGifticonDialog.show()
    }
}