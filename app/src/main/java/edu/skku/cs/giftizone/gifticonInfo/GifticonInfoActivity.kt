package edu.skku.cs.giftizone.gifticonInfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.dataClass.Gifticon

class GifticonInfoActivity : AppCompatActivity() {
    private var gifticon: Gifticon? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon_info)

        setupGifticonInfoImage()
    }

    private fun setupGifticonInfoImage() {
        gifticon = intent.getParcelableExtra("gifticon")
        Glide.with(this)
            .load(gifticon?.imagePath)
            .into(findViewById(R.id.gifticonInfoImage))
    }
}