package edu.skku.cs.giftizone.addGifticon

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import edu.skku.cs.giftizone.R

class AddGifticonActivity : AppCompatActivity() {
    private val pickImageResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        val gifticonImage = findViewById<ImageView>(R.id.addGifticonImage)
        if (uri != null) {
            Glide.with(this)
                .load(uri)  // or Uri or File
                .into(gifticonImage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gifticon)

        pickImageFromGallery()
    }

    private fun pickImageFromGallery() {
        pickImageResultLauncher.launch("image/*")
    }
}