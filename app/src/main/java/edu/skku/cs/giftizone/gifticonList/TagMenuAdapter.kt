package edu.skku.cs.giftizone.gifticonList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.giftizone.R

class TagMenuAdapter(
    private val tagList: ArrayList<String>,
    private val removeTagHandler: (String) -> Unit,
    private val context: Context
) :
    RecyclerView.Adapter<TagMenuAdapter.TagMenuHolder>() {

    inner class TagMenuHolder(tagItemView: View) : RecyclerView.ViewHolder(tagItemView) {
        val removeTagText: TextView
        private val removeTagButton: ImageButton
        init {
            removeTagText = tagItemView.findViewById(R.id.removeTagText)
            removeTagButton = tagItemView.findViewById(R.id.removeTagButton)
            removeTagButton.setOnClickListener {
                removeTagHandler(removeTagText.text.toString())
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TagMenuHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.tag_menu_item, viewGroup, false)
        return TagMenuHolder(view)
    }

    override fun onBindViewHolder(holder: TagMenuHolder, position: Int) {
        holder.removeTagText.text = tagList[position]
    }

    override fun getItemCount(): Int = tagList.size
}