package edu.skku.cs.giftizone.gifticonList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.dataClass.Gifticon

class GifticonListAdapter(private val gifticonList: ArrayList<Gifticon>) :
    RecyclerView.Adapter<GifticonListAdapter.GifticonViewHolder>() {

    inner class GifticonViewHolder(gifticonItemView: View) : RecyclerView.ViewHolder(gifticonItemView) {
        init {

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GifticonViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.tag_item, viewGroup, false)
        return GifticonViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifticonViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = gifticonList.size
}