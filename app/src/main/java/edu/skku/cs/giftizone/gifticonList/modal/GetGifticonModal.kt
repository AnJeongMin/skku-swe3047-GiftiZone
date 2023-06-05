package edu.skku.cs.giftizone.gifticonList.modal

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.common.Gifticon
import edu.skku.cs.giftizone.common.toast
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate

class GetGifticonModal(
    private val context: Context,
    private val addGifticonHandler: (Gifticon) -> Unit,
) {
    private val inflater = LayoutInflater.from(context)
    private val dialogLayout = inflater.inflate(R.layout.get_gifticon_modal, null)
    private val getGifticonDialog = androidx.appcompat.app.AlertDialog.Builder(context).create()
    private val getGifticonConfirmBtn = dialogLayout
        .findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.getGifticonConfirmButton)
    private val shareId = dialogLayout
        .findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.shareIdEdit)

    init {
        getGifticonDialog.setView(dialogLayout)
        getGifticonConfirmBtn.setOnClickListener {
            requestGetGifticon(shareId.text.toString())
        }
    }

    fun show() {
        getGifticonDialog.show()
    }

    private fun requestGetGifticon(shareId: String) {
        val client = OkHttpClient()
        val url = "***REMOVED***/share/gifticon?shareId=$shareId"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                toast(context, "서버와의 연결이 원활하지 않습니다.")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (body == null) {
                    toast(context, "서버와의 연결이 원활하지 않습니다.")
                    return
                }

                val share = JSONObject(body).getString("share")
                if (share == "false") {
                    toast(context, "해당 share id와 관련된 gifticon이 없습니다.")
                    return
                }
                val gifticon = JSONObject(body).getJSONObject("gifticon")

                val id = gifticon.getString("id")
                val provider = gifticon.getString("provider")
                val content = gifticon.getString("content")
                val barcode = gifticon.getString("barcode")
                val expiredAt = LocalDate.parse(gifticon.getString("expiredAt"))
                val createAt = LocalDate.parse(gifticon.getString("createAt"))
                val tag = gifticon.getString("tag")

                val newGifticon = Gifticon("", barcode, tag, provider, content, expiredAt, createAt, id)

                Handler(Looper.getMainLooper()).post {
                    addGifticonHandler(newGifticon)
                }

                toast(context, "gifticon이 추가되었습니다.")
            }
        })
    }
}