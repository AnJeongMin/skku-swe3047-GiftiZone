package edu.skku.cs.giftizone.gifticonList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.enums.Filter

class GifticonListActivity : AppCompatActivity() {
//    private val tagList = ArrayList<String>()
    private val tagList = arrayListOf("tag1", "tagtag2")
    private val selectedTag = HashSet<String>()

    private var filter = Filter.LIMIT

    private var tagRecyclerView: RecyclerView? = null
    private var gifticonRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon_list)

        setupAddTagModal()
        setupTagMenuModal()
        setupDropdown()

        setupTagRecycleView()
        setupGifticonRecycleView()
    }

    private fun setupAddTagModal() {
        val tagAddBtn = findViewById<ImageButton>(R.id.TagAddButton)
        tagAddBtn.setOnClickListener {
            val addTagModal = AddTagModal(this) { tag ->
                tagList.add(tag)
            }
            addTagModal.show()
        }
    }

    private fun setupTagMenuModal() {
        val tagMenuBtn = findViewById<ImageButton>(R.id.tagMenuButton)
        tagMenuBtn.setOnClickListener {
            val tagMenuModal = TagMenuModal(this, tagList) {
                tagList.remove(it)
                selectedTag.remove(it)
                updateRecycleTagData()
            }
            tagMenuModal.show()
        }
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

    private fun setupTagRecycleView() {
        tagRecyclerView = findViewById(R.id.tagListView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        tagRecyclerView?.layoutManager = layoutManager
        tagRecyclerView?.adapter = TagAdapter(tagList, selectedTag)
    }

    private fun setupGifticonRecycleView() {
        gifticonRecyclerView = findViewById(R.id.gifticonListView)
        gifticonRecyclerView?.adapter = TagAdapter(tagList, selectedTag)
    }

    private fun updateRecycleTagData() {
        tagRecyclerView?.adapter?.notifyDataSetChanged()
    }
}