package com.example.mannayoclient.alarmlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R

class AlarmRVAdapter(val items: ArrayList<AlarmModel>) :
    RecyclerView.Adapter<AlarmRVAdapter.Viewholder>() {

    //아이템 가져오기
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.alarm_item, parent, false)
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

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: AlarmModel) {

            val alarm1 = itemView.findViewById<TextView>(R.id.alarm1)
            alarm1.text = item.Title

            val alarm2 = itemView.findViewById<TextView>(R.id.alarm2)
            alarm2.text = item.contents

            val date = itemView.findViewById<TextView>(R.id.date)
            date.text = item.date
        }

    }
}