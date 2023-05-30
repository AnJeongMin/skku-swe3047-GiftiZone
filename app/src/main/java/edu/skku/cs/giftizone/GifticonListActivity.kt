package edu.skku.cs.giftizone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GifticonListActivity : AppCompatActivity() {
    private val tagList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon_list)

        val tagAddBtn = findViewById<Button>(R.id.TagAddButton)
        tagAddBtn.setOnClickListener {
            val tagModal = TagModal(this) { tag ->
                tagList.add(tag)
            }
            tagModal.show()
        }

        val tagListView = findViewById<RecyclerView>(R.id.tagListView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        tagListView.layoutManager = layoutManager
        tagListView.adapter = TagAdapter(tagList)
    }
}