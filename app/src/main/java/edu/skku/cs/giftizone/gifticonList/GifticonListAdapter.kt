package edu.skku.cs.giftizone.gifticonList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.dataClass.Gifticon
import java.time.LocalDate
import java.time.Period

class GifticonListAdapter(
    private val gifticonList: ArrayList<Gifticon>,
    private val selectedTag: HashSet<String>
    ) :
    RecyclerView.Adapter<GifticonListAdapter.GifticonViewHolder>(), Filterable {

    inner class GifticonViewHolder(gifticonItemView: View) : RecyclerView.ViewHolder(gifticonItemView) {
        val gifticonImage = gifticonItemView.findViewById<ImageView>(R.id.gifticonImage)
        val gifticonProvider = gifticonItemView.findViewById<TextView>(R.id.providerText)
        val gifticonContent = gifticonItemView.findViewById<TextView>(R.id.contentText)
        val gifticonExpireDate = gifticonItemView.findViewById<TextView>(R.id.expireDateText)
        val gifticonDday = gifticonItemView.findViewById<TextView>(R.id.dDayText)
        val gifticonRemoveBtn = gifticonItemView.findViewById<ImageButton>(R.id.gifticonRemoveButton)

        init {
            gifticonRemoveBtn.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GifticonViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.gifticon_item, viewGroup, false)
        return GifticonViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifticonViewHolder, position: Int) {
        val gifticon = gifticonList[position]
//        holder.gifticonImage.setBackgroundResource(gifticon.imagePath)
        holder.gifticonProvider.text = gifticon.provider
        holder.gifticonContent.text = gifticon.content
        holder.gifticonExpireDate.text = gifticon.expiredAt.toString()

        val dDay = Period.between(LocalDate.now(), gifticon.expiredAt).days
        if (dDay < 0) {
            holder.itemView.setBackgroundColor(Color.GRAY)
            holder.itemView.findViewById<TextView>(R.id.expiredText).visibility = View.VISIBLE
        } else {
            holder.gifticonDday.text = "D-${dDay}"
        }
    }

    override fun getItemCount(): Int = gifticonList.size
    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}