package edu.skku.cs.giftizone.gifticonList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.addGifticon.AddGifticonActivity
import edu.skku.cs.giftizone.dataClass.Gifticon
import edu.skku.cs.giftizone.enums.SortFilter
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class GifticonListActivity : AppCompatActivity() {
//    private val tagList = ArrayList<String>()
//    private val gifticonList = ArrayList<Gifticon>()
    private val selectedTag = HashSet<String>()

    private val tagList = arrayListOf("tag1", "tag2", "tag3")
    private val gifticonList = arrayListOf(Gifticon("", "123-123", "tag1", "ABQ", "황올1", LocalDate.now().plus(1, ChronoUnit.WEEKS)),
    Gifticon("", "123-123", "tag2", "BBQ", "황올2", LocalDate.now().plus(-3, ChronoUnit.WEEKS), LocalDate.now().plus(-3, ChronoUnit.DAYS)),
    Gifticon("", "123-123", "tag3", "CBQ", "황올3", LocalDate.now().plus(1, ChronoUnit.WEEKS), LocalDate.now().plus(-4, ChronoUnit.DAYS)))


    private var sortFilter = SortFilter.LIMIT

    private var tagRecyclerView: RecyclerView? = null
    private var gifticonRecyclerView: RecyclerView? = null
    private val recyclerViewList: ArrayList<RecyclerView> = ArrayList()

    private val REQUEST_SELECT_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon_list)

        setupAddTagModal()
        setupTagMenuModal()
        setupDropdown()

        setupTagRecycleView()
        setupGifticonRecycleView()

        setupAddGifticonActivity()
    }

    private fun setupAddTagModal() {
        val tagAddBtn = findViewById<ImageButton>(R.id.TagAddButton)
        tagAddBtn.setOnClickListener {
            val addTagModal = AddTagModal(this, tagList) { tag ->
                tagList.add(tag)
                updateRecycleData()
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
                updateRecycleData()
                updateGifticonFilter()
            }
            tagMenuModal.show()
        }
    }

    private fun setupDropdown() {
        val filterDropdown: Spinner = findViewById(R.id.filterDropdown)

        filterDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                sortFilter = SortFilter.values()[position]
                updateGifticonFilter()

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                sortFilter = SortFilter.LIMIT
                updateGifticonFilter()
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
        tagRecyclerView?.adapter = TagAdapter(tagList, selectedTag) {
            updateGifticonFilter()
        }

        recyclerViewList.add(tagRecyclerView!!)
    }

    private fun setupGifticonRecycleView() {
        gifticonRecyclerView = findViewById(R.id.gifticonListView)
        val layoutManager = LinearLayoutManager(this)
        gifticonRecyclerView?.layoutManager = layoutManager
        gifticonRecyclerView?.adapter = GifticonListAdapter(this, gifticonList, selectedTag, sortFilter)

        recyclerViewList.add(gifticonRecyclerView!!)
    }

    private fun updateRecycleData() {
        recyclerViewList.forEach {
            it.adapter?.notifyDataSetChanged()
        }
    }

    private fun updateGifticonFilter() {
        val adapter = gifticonRecyclerView?.adapter as? GifticonListAdapter
        adapter?.setSortFilter(sortFilter)
        adapter?.filter?.filter(null)
    }

    private fun setupAddGifticonActivity() {
        val addGifticonBtn = findViewById<ImageView>(R.id.addGifticonButton)
        addGifticonBtn.setOnClickListener {
            val intent = Intent(this, AddGifticonActivity::class.java)

            intent.putExtra("tagList", tagList)
            startActivity(intent)
        }
    }
}