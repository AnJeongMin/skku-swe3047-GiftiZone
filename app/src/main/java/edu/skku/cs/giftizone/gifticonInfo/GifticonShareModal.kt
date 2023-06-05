package edu.skku.cs.giftizone.gifticonInfo

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.common.BaseModal

class GifticonShareModal(
    private val context: Context,
    private val shareId: String,
): BaseModal(context, R.layout.gifticon_share_modal) {
    private val shareIdText = dialogLayout.findViewById<AppCompatTextView>(R.id.shareIdText)

    init {
        shareIdText.text = shareId
        dialog.setView(dialogLayout)
    }
}