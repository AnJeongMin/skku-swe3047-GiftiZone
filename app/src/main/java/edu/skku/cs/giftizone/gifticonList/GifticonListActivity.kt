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
import edu.skku.cs.giftizone.dataClass.Gifticon
import edu.skku.cs.giftizone.enums.Filter
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class GifticonListActivity : AppCompatActivity() {
//    private val tagList = ArrayList<String>()
//    private val gifticonList = ArrayList<Gifticon>()
    private val selectedTag = HashSet<String>()

    private val tagList = arrayListOf("tag1", "tag2", "tag3")
    private val gifticonList = arrayListOf(Gifticon("", "123-123", "tag1", "BBQ", "황올1", LocalDate.now().plus(1, ChronoUnit.WEEKS)),
    Gifticon("", "123-123", "tag2", "BBQ", "황올2", LocalDate.now().plus(1, ChronoUnit.WEEKS)),
    Gifticon("", "123-123", "tag3", "BBQ", "황올3", LocalDate.now().plus(1, ChronoUnit.WEEKS)))


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
            val addTagModal = AddTagModal(this, tagList) { tag ->
                tagList.add(tag)
                updateRecycleTagData()
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
        gifticonRecyclerView?.adapter = GifticonListAdapter(gifticonList, selectedTag)
    }

    private fun updateRecycleTagData() {
        tagRecyclerView?.adapter?.notifyDataSetChanged()
    }
}