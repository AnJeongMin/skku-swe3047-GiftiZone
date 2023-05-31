package edu.skku.cs.giftizone.gifticonList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.giftizone.R

class TagAdapter(private val tagList: ArrayList<String>, private val selectedTag: HashSet<String>) :
    RecyclerView.Adapter<TagAdapter.TagViewHolder>() {
    private val MAX_SELECTED_TAG_COUNT = 3

    inner class TagViewHolder(tagItemView: View) : RecyclerView.ViewHolder(tagItemView) {
        val tagButton: Button
        init {
            tagButton = tagItemView.findViewById(R.id.tagButton)
            tagButton.setOnClickListener {
                val tag = tagButton.text.toString()
                if (selectedTag.contains(tag)) {
                    tagButton.setBackgroundResource(R.drawable.tag_background)
                    tagButton.setTextColor(ContextCompat.getColor(tagButton.context, R.color.gray))
                    selectedTag.remove(tag)
                } else {
                    if (selectedTag.size >= MAX_SELECTED_TAG_COUNT) {
                        Toast.makeText(tagButton.context, "태그는 3개까지 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    tagButton.setBackgroundResource(R.drawable.tag_background_selected)
                    tagButton.setTextColor(ContextCompat.getColor(tagButton.context, R.color.white))
                    selectedTag.add(tag)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.tag_item, viewGroup, false)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.tagButton.text = tagList[position]
    }

    override fun getItemCount(): Int = tagList.size
}
