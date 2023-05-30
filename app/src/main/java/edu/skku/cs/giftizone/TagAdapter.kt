package edu.skku.cs.giftizone

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TagAdapter(private val tagList: ArrayList<String>, private val selectedTag: HashSet<String>) :
    RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

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
