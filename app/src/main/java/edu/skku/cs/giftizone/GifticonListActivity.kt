package edu.skku.cs.giftizone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class GifticonListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon_list)

        val tagAddBtn = findViewById<Button>(R.id.TagAddButton)
        tagAddBtn.setOnClickListener {
            val tagModal = TagModal(this) { tag ->
                Toast.makeText(this, tag, Toast.LENGTH_SHORT).show()
            }
            tagModal.show()
        }
    }
}