package edu.skku.cs.giftizone.gifticonList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.common.Gifticon
import edu.skku.cs.giftizone.common.toast
import edu.skku.cs.giftizone.enums.SortFilter
import edu.skku.cs.giftizone.gifticonList.modal.GifticonRemoveModal
import java.io.File
import java.time.LocalDate
import java.time.Period
import java.time.format.TextStyle
import java.util.Locale

class GifticonListAdapter(
    private val context: Context,
    private val originGifticonList: ArrayList<Gifticon>,
    private val selectedTag: HashSet<String>,
    private var sortFilter: SortFilter,
    private val gifticonInfoHandler: (gifticon: Gifticon) -> Unit,
    private val gifticonRemoveHandler: (gifticon: Gifticon) -> Unit,
    ) :
    RecyclerView.Adapter<GifticonListAdapter.GifticonViewHolder>(), Filterable {

    private var filteredGifticonList: List<Gifticon> = ArrayList(originGifticonList)

    inner class GifticonViewHolder(gifticonItemView: View) : RecyclerView.ViewHolder(gifticonItemView) {
        val gifticonImage = gifticonItemView.findViewById<ImageView>(R.id.gifticonImage)
        val gifticonProvider = gifticonItemView.findViewById<TextView>(R.id.providerText)
        val gifticonContent = gifticonItemView.findViewById<TextView>(R.id.contentText)
        val gifticonExpireDate = gifticonItemView.findViewById<TextView>(R.id.expireDateText)
        val gifticonDday = gifticonItemView.findViewById<TextView>(R.id.dDayText)
        val gifticonRemoveBtn = gifticonItemView.findViewById<ImageButton>(R.id.gifticonRemoveButton)

        init {
            gifticonItemView.setOnClickListener {
                val selectedGifticon = filteredGifticonList[adapterPosition]
                val dDay = Period.between(LocalDate.now(), selectedGifticon.expiredAt).days
                if (dDay < 0) {
                    toast(context, "기간이 만료된 기프티콘입니다.")
                    return@setOnClickListener
                }
                gifticonInfoHandler(selectedGifticon)
            }
            gifticonRemoveBtn.setOnClickListener {
                val selectedGifticon = filteredGifticonList[adapterPosition]
                val gifticonRemoveModal = GifticonRemoveModal(context, selectedGifticon) {
                    originGifticonList.removeIf { gifticon -> gifticon.id == selectedGifticon.id }
                    gifticonRemoveHandler(selectedGifticon)
                    filter.filter(null)
                }
                gifticonRemoveModal.show()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GifticonViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.gifticon_item, viewGroup, false)
        return GifticonViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifticonViewHolder, position: Int) {
        val gifticon = filteredGifticonList[position]
        if (gifticon.imagePath == "") {
            holder.gifticonImage.setImageResource(R.drawable.baseline_web_asset_off_24)
        } else {
            Glide.with(context)
                .load(File(gifticon.imagePath))
                .into(holder.gifticonImage)
        }
        holder.gifticonProvider.text = gifticon.provider
        holder.gifticonContent.text = gifticon.content
        val weekDay = gifticon.expiredAt.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
        holder.gifticonExpireDate.text = "${gifticon.expiredAt} (${weekDay})"
        val dDay = Period.between(LocalDate.now(), gifticon.expiredAt).days
        holder.gifticonDday.text = "D-${dDay}"
        if (dDay < 0) {
            val blindText = holder.itemView.findViewById<TextView>(R.id.blindText)
            blindText.visibility = View.VISIBLE
            holder.gifticonDday.visibility = View.GONE
        } else {
            val blindText = holder.itemView.findViewById<TextView>(R.id.blindText)
            blindText.visibility = View.INVISIBLE
            holder.gifticonDday.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = filteredGifticonList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<Gifticon>()
                if (selectedTag.isEmpty()) {
                    filteredList.addAll(originGifticonList)
                } else {
                    for (item in originGifticonList) {
                        if (selectedTag.contains(item.tag)) {
                            filteredList.add(item)
                        }
                    }
                }

                when (sortFilter) {
                    SortFilter.LIMIT -> {
                        filteredList.sortBy { it.expiredAt }
                    }
                    SortFilter.SAVE -> {
                        filteredList.sortBy { it.createAt }
                    }
                    SortFilter.ALPHA -> {
                        filteredList.sortBy { it.provider }
                    }
                }

                val results = FilterResults()
                results.values = filteredList

                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredGifticonList = results?.values as ArrayList<Gifticon>
                notifyDataSetChanged()
            }
        }
    }

    fun setSortFilter(sortFilter: SortFilter) {
        this.sortFilter = sortFilter
    }
}