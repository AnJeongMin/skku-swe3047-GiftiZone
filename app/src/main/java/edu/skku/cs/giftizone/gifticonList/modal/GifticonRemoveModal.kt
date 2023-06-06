package edu.skku.cs.giftizone.gifticonList.modal

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.common.BaseModal
import edu.skku.cs.giftizone.common.Gifticon
import java.io.File

class GifticonRemoveModal(
    private val context: Context,
    private val gifticon: Gifticon,
    private val removeGifticonHandler: () -> Unit,
): BaseModal(context, R.layout.gifticon_remove_modal) {
    private val gifticonRemoveBtn = dialogLayout.findViewById<Button>(R.id.removeGifticonCancelButton)
    private val removeGifticonImage = dialogLayout.findViewById<ImageView>(R.id.removeGifticonImage)
    private val removeGifticonProvider = dialogLayout.findViewById<TextView>(R.id.removeGifticonProvider)
    private val removeGifticonContent = dialogLayout.findViewById<TextView>(R.id.removeGifticonContent)

    init {
        if (gifticon.imagePath == "") {
            removeGifticonImage.setImageResource(R.drawable.baseline_web_asset_off_24)
        } else {
            Glide.with(context)
                .load(gifticon.imagePath)
                .into(removeGifticonImage)
        }
        removeGifticonProvider.text = gifticon.provider
        removeGifticonContent.text = gifticon.content

        gifticonRemoveBtn.setOnClickListener {
            removeGifticonHandler()
            deleteGifticonImageFile(gifticon.imagePath)
            dialog.dismiss()
        }

        dialog.setView(dialogLayout)
    }

    private fun deleteGifticonImageFile(path: String) {
        val file = File(path)
        if (file.exists())
            file.delete()
    }
}