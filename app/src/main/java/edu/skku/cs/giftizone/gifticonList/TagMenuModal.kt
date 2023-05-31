package edu.skku.cs.giftizone.gifticonList

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import edu.skku.cs.giftizone.R

class TagMenuModal(
    private val context: Context,
    private val tagList: ArrayList<String>,
    private val removeTagHandler: (String) -> Unit,
){
    private val inflater = LayoutInflater.from(context)
    private val dialogLayout = inflater.inflate(R.layout.tag_menu_modal, null)
    private val cancelTagMenuBtn = dialogLayout
        .findViewById<androidx.appcompat.widget.AppCompatImageButton>(R.id.tagMenuCancelButton)
    private val tagDialog = androidx.appcompat.app.AlertDialog.Builder(context).create()

    init {
        tagDialog.setView(dialogLayout)
        cancelTagMenuBtn.setOnClickListener {
            tagDialog.cancel()
        }

        setupTagMenuRecycleView()
    }

    private fun setupTagMenuRecycleView() {
        val tagRecycleView = dialogLayout.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.tagMenuList)
        tagRecycleView.adapter = TagMenuAdapter(tagList, removeTagHandler)
        tagRecycleView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
    }

    fun show() {
        tagDialog.show()
    }
}