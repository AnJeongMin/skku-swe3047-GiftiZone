package edu.skku.cs.giftizone.gifticonList

import android.graphics.Color
import android.util.Log
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
import edu.skku.cs.giftizone.enums.SortFilter
import java.time.LocalDate
import java.time.Period
import java.time.format.TextStyle
import java.util.Locale

class GifticonListAdapter(
    private val fullGifticonList: ArrayList<Gifticon>,
    private val selectedTag: HashSet<String>,
    private val filter: SortFilter
    ) :
    RecyclerView.Adapter<GifticonListAdapter.GifticonViewHolder>(), Filterable {

    private var gifticonList: List<Gifticon> = ArrayList(fullGifticonList)

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

        val weekDay = gifticon.expiredAt.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
        holder.gifticonExpireDate.text = "${gifticon.expiredAt} (${weekDay})"
        val dDay = Period.between(LocalDate.now(), gifticon.expiredAt).days
        holder.gifticonDday.text = "D-${dDay}"
        if (dDay < 0) {
            holder.itemView.setBackgroundColor(Color.GRAY)
            holder.itemView.alpha = 0.5f
            holder.gifticonDday.visibility = View.GONE
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
            holder.itemView.alpha = 1f
            holder.gifticonDday.visibility = View.VISIBLE

        }
    }

    override fun getItemCount(): Int = gifticonList.size
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<Gifticon>()
                if (selectedTag.isEmpty()) {
                    filteredList.addAll(fullGifticonList)
                } else {
                    for (item in fullGifticonList) {
                        if (selectedTag.contains(item.tag)) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList

                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                gifticonList = results?.values as ArrayList<Gifticon>
                notifyDataSetChanged()
            }
        }
    }
}