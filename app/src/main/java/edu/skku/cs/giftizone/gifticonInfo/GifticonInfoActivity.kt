package edu.skku.cs.giftizone.gifticonInfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.dataClass.Gifticon
import edu.skku.cs.giftizone.gifticonMap.GifticonMapActivity
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

class GifticonInfoActivity : AppCompatActivity() {
    private var gifticon: Gifticon? = null
    private val SHARE_DELAY = 60 // 60s
    private var shareDelay = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon_info)

        setupGifticonInfoImage()
        setupGifticonMapButton()
        setupGifticonShareButton()
    }

    private fun setupGifticonInfoImage() {
        gifticon = intent.getParcelableExtra("gifticon")
        Glide.with(this)
            .load(gifticon?.imagePath)
            .into(findViewById(R.id.gifticonInfoImage))
    }

    private fun setupGifticonMapButton() {
        val gifticonMapButton = findViewById<ImageView>(R.id.gifticonInfoMapButton)
        gifticonMapButton.setOnClickListener {
            val intent = Intent(this, GifticonMapActivity::class.java)
            intent.putExtra("provider", gifticon?.provider)
            startActivity(intent)
        }
    }

    private fun setupGifticonShareButton() {
        val gifticonShareButton = findViewById<ImageView>(R.id.gifticonInfoShareButton)
        gifticonShareButton.setOnClickListener {
            if (!isShareDelay())
                return@setOnClickListener

            val shareId = getShareId()
            val gifticonShareModal = GifticonShareModal(this, shareId)
            requestGifticonShare(gifticon!!, shareId)
            gifticonShareModal.show()
        }
    }

    private fun isShareDelay(): Boolean {
        if (shareDelay > 0) {
            toast("공유는 ${shareDelay}초 후에 가능합니다.")
            return false
        }
        shareDelay = SHARE_DELAY
        val scope = CoroutineScope(Dispatchers.Default) // Replace with your own scope
        scope.launch {
            while (shareDelay > 0) {
                delay(1000L) // Delay for 1 second
                shareDelay -= 1
            }
        }
        return true
    }

    private fun requestGifticonShare(gifticon: Gifticon, shareId: String) {
        val client = OkHttpClient()
        val url = "http://localhost:4000/share/gifticon"

        val jsonObject = JsonObject()
        val imagePath = gifticon.imagePath
        jsonObject.add("gifticon", Gson().toJsonTree(gifticon))
        jsonObject.addProperty("image", encodeImageToBase64(imagePath))
        jsonObject.addProperty("shareId", shareId)
        val requestBody = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

        print("===========================================")
        print(jsonObject.toString())
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

    private fun getShareId(): String {
        val random = Random(System.currentTimeMillis())
        val randomNumber = random.nextInt(1000000)

        return String.format("%06d", randomNumber)
    }

    private fun encodeImageToBase64(imagePath: String): String {
        val imageBytes = Files.readAllBytes(Paths.get(imagePath))
        return Base64.getEncoder().encodeToString(imageBytes)
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}