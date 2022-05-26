package com.example.mannayoclient.storemenulist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mannayoclient.R

class StoreMenuRVAdapter  (val items : ArrayList<StoreMenuModel>) : RecyclerView.Adapter<StoreMenuRVAdapter.Viewholder>(){

    //아이템 가져오기
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
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

        fun bindItems(item: StoreMenuModel) {

            val menu_title = itemView.findViewById<TextView>(R.id.menu_text)
            menu_title.text = item.title

            val mainmenu_image = itemView.findViewById<ImageView>(R.id.imageView8)
            Glide.with(itemView).load(item.image).into(mainmenu_image)

        }

    }
}