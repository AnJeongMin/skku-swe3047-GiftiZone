package edu.skku.cs.giftizone.gifticonInfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.dataClass.Gifticon
import edu.skku.cs.giftizone.gifticonMap.GifticonMapActivity

class GifticonInfoActivity : AppCompatActivity() {
    private var gifticon: Gifticon? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon_info)

        setupGifticonInfoImage()
        setupGifticonMapButton()
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
}