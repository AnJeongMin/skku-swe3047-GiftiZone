package edu.skku.cs.giftizone.gifticonList

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
    private val removeGifticonImage = dialogLayout.findViewById<ImageView>(R.id.removeGifticonImage)
    private val removeGifticonProvider = dialogLayout.findViewById<TextView>(R.id.removeGifticonProvider)
    private val removeGifticonContent = dialogLayout.findViewById<TextView>(R.id.removeGifticonContent)
    private val removeGifticonDialog = androidx.appcompat.app.AlertDialog.Builder(context).create()

    init {
        removeGifticonDialog.setView(dialogLayout)
//        removeGifticonImage.setImageBitmap(gifticon.image)
        removeGifticonProvider.text = gifticon.provider
        removeGifticonContent.text = gifticon.content

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