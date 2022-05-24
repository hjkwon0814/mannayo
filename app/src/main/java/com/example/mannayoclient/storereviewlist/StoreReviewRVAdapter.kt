package com.example.mannayoclient.storereviewlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mannayoclient.R


class StoreReviewRVAdapter (val items : ArrayList<StoreReviewModel>) : RecyclerView.Adapter<StoreReviewRVAdapter.Viewholder>(){

    //아이템 가져오기
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return Viewholder(v)
    }

    //아이템 연결
    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        holder.bindItems(items[position])
    }

    //전체 아이템의 갯수
    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item: StoreReviewModel) {

            val r_name = itemView.findViewById<TextView>(R.id.r_name)
            r_name.text = item.name

            val r_date = itemView.findViewById<TextView>(R.id.r_date)
            r_date.text = item.date

            val r_grade= itemView.findViewById<TextView>(R.id.r_grade)
            r_grade.text = item.grade

            val r_content = itemView.findViewById<TextView>(R.id.r_content)
            r_content.text = item.content

            val r_image = itemView.findViewById<ImageView>(R.id.imageView45)
            Glide.with(itemView).load(item.image).into(r_image)

            val r_member_image = itemView.findViewById<ImageView>(R.id.imageView59)
            Glide.with(itemView).load(item.memberImage).into(r_member_image)

        }

    }
}