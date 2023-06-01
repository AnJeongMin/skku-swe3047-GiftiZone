package edu.skku.cs.giftizone.gifticonInfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.dataClass.Gifticon
import edu.skku.cs.giftizone.gifticonMap.GifticonMapActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

            val gifticonShareModal = GifticonShareModal(this, gifticon!!)
            gifticonShareModal.show()
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
}