package edu.skku.cs.giftizone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.giftizone.enums.Filter

class GifticonListActivity : AppCompatActivity() {
//    private val tagList = ArrayList<String>()
    private val tagList = arrayListOf("tag1", "tagtag2")
    private val selectedTag = HashSet<String>()
    private var filter = Filter.LIMIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon_list)

        setupTagModal()
        setupTagRecycleView()
        setupDropdown()
    }

    private fun setupTagModal() {
        val tagAddBtn = findViewById<ImageButton>(R.id.TagAddButton)
        tagAddBtn.setOnClickListener {
            val tagModal = TagModal(this) { tag ->
                tagList.add(tag)
            }
            tagModal.show()
        }
    }

    private fun setupTagRecycleView() {
        val tagListView = findViewById<RecyclerView>(R.id.tagListView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        tagListView.layoutManager = layoutManager
        tagListView.adapter = TagAdapter(tagList, selectedTag)
    }

    private fun setupDropdown() {
        val filterDropdown: Spinner = findViewById(R.id.filterDropdown)

        filterDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filter = Filter.values()[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                filter = Filter.LIMIT
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.gifticon_filter,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            filterDropdown.adapter = adapter
        }
    }
}