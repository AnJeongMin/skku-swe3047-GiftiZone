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
import java.util.*

class GifticonShareModal(
    private val context: Context,
    private val gifticon: Gifticon,
){
    private val inflater = LayoutInflater.from(context)
    private val dialogLayout = inflater.inflate(R.layout.gifticon_share_modal, null)
    private val shareIdText = dialogLayout.findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.shareIdText)
    private val shareDialog = androidx.appcompat.app.AlertDialog.Builder(context).create()

    init {
        shareDialog.setView(dialogLayout)
        shareIdText.text = getShareId()
//        requestGifticonShare(gifticon, shareIdText.text.toString())
    }

    fun show() {
        shareDialog.show()
    }

    private fun getShareId(): String {
        val random = Random(System.currentTimeMillis())
        val randomNumber = random.nextInt(1000000)

        return String.format("%06d", randomNumber)
    }

    private fun requestGifticonShare(gifticon: Gifticon, shareId: String) {
        val client = OkHttpClient()
        val url = "YOUR_POST_URL"

        val jsonObject = JsonObject()
        jsonObject.add("gifticon", Gson().toJsonTree(gifticon))
        jsonObject.addProperty("shareId", shareId)
        val requestBody = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                toast("공유가 정상적으로 되었습니다.")
            }
        })
    }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}