package edu.skku.cs.giftizone.gifticonInfo

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.dataClass.Gifticon
import edu.skku.cs.giftizone.gifticonList.TagMenuAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

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