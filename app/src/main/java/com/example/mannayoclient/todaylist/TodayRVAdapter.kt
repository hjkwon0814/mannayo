package com.example.mannayoclient.todaylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mannayoclient.R

class TodayRVAdapter(private val items: List<TodayModel>) :
    RecyclerView.Adapter<TodayRVAdapter.Viewholder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.advertise_item, parent, false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        if (itemClick != null) {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: TodayModel) {

            val nickname = itemView.findViewById<TextView>(R.id.nickname)
            nickname.text = item.nickname

            val date = itemView.findViewById<TextView>(R.id.date)
            date.text = item.date

            val mainText = itemView.findViewById<TextView>(R.id.main_text)
            mainText.text = item.mainText

            val image = itemView.findViewById<ImageView>(R.id.imageView74)
            Glide.with(itemView).load(item.image).into(image)

            val like = itemView.findViewById<TextView>(R.id.like)
            like.text = item.like.toString()

            val comment = itemView.findViewById<TextView>(R.id.chat)
            comment.text = item.chat.toString()


        }

    }
}
