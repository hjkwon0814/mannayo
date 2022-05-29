package com.example.mannayoclient.todaylist.today_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R

class TodayVoteRVAdapter  (val items : ArrayList<TodayVoteModel>) : RecyclerView.Adapter<TodayVoteRVAdapter.Viewholder>(){

    //아이템 가져오기
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vote_item, parent, false)
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

        fun bindItems(item: TodayVoteModel) {

            val vote_title = itemView.findViewById<TextView>(R.id.choice)
            vote_title.text = item.contents

            val vote_count = itemView.findViewById<TextView>(R.id.vote_count)
            vote_count.text = item.count.toString() + "명"

            val vote_check = itemView.findViewById<CheckBox>(R.id.checkBox5)
            val amIVote = item.amIVote
            vote_check.isChecked = amIVote


        }

    }
}