package com.example.mannayoclient.todaylist.today_detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R


class TodayReplyRVAdapter(var list: ArrayList<TodayReplyModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var mSelectedItem = -1

    //아이템 클릭
    interface ItemClick {
        fun onChatClick(view :View, position: Int)
        fun onNickClick(view :View, position: Int)
        fun onDeleteClick(view: View, position: Int)
    }
    var itemClick : ItemClick? = null



    //아이템 가져오기
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view: View?
        return when(viewType) {
            TodayReplyModel.reply1 ->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.reply_item, parent, false)
                Reply1ViewHolder(view)
            }
            TodayReplyModel.reply2->{
               view = LayoutInflater.from(parent.context).inflate(R.layout.reply2_item, parent, false)
                Reply2ViewHolder(view)
            }
            else->{
                throw RuntimeException("알 수 없는 viewtype error")
            }


        }
    }

    //전체 아이템의 갯수
    override fun getItemCount(): Int {
        return list.size
    }

    fun setItem(item: ArrayList<TodayReplyModel>) {
        list = item
        mSelectedItem = -1
        notifyDataSetChanged()
    }

    //아이템 연결
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val obj = list[position]
            when (obj.type) {
                TodayReplyModel.reply1 -> {
                    (holder as Reply1ViewHolder).reply_name.setText(obj.name)
                    holder.reply_date.setText(obj.date)
                    holder.reply.setText(obj.reply)
                    //holder.reply_image.setImageBitmap(obj.image)

//                    if(list[position].writerid != list[position].memberid) {
//                        holder.itemView.findViewById<ImageView>(R.id.delete).visibility = View.GONE
//                    }

                    if (itemClick != null) {
                        holder.itemView.findViewById<ImageView>(R.id.chat_send).setOnClickListener {
                                v -> itemClick?.onChatClick(v, position)
                            var count = 0
                            for(i in list) {
                                i.count = 0
                                if(i.isClicked) {
                                    count +=1
                                }
                            }
                            println(4)
                            if(count >= 1) {
                                for(i in list) {
                                    i.count = 1
                                }
                            }
                            println(5)
                            if(obj.isClicked) {
                                holder.itemView.findViewById<ConstraintLayout>(R.id.replyconst)
                                    .setBackgroundColor(
                                        Color.parseColor("#FDF4F4"))
                            } else {
                                holder.itemView.findViewById<ConstraintLayout>(R.id.replyconst)
                                    .setBackgroundColor(
                                        Color.parseColor("#ffffff"))
                            }
                        }
                        holder.itemView.findViewById<TextView>(R.id.reply_name).setOnClickListener{
                                v -> itemClick?.onNickClick(v, position)
                        }
                        holder.itemView.findViewById<ImageView>(R.id.chat_delete).setOnClickListener {
                            v -> itemClick?.onDeleteClick(v, position)
                        }

                    }
                }
                TodayReplyModel.reply2 -> {
                    (holder as Reply2ViewHolder).reply2_name.setText(obj.name)
                    holder.reply2_date.setText(obj.date)
                    holder.reply2.setText(obj.reply)
                    //holder.reply_image.setImageBitmap(obj.image)
                    if(itemClick != null){
                        holder.itemView.findViewById<TextView>(R.id.reply2_name).setOnClickListener{
                                v -> itemClick?.onNickClick(v, position)
                        }
                    }
                }

            }

        }



    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    inner class Reply1ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        val reply_name = itemView.findViewById<TextView>(R.id.reply_name)
        val reply_date = itemView.findViewById<TextView>(R.id.reply_date)
        val reply = itemView.findViewById<TextView>(R.id.reply)
        //val reply_image = itemView.findViewById<ImageView>(R.id.reply_image)

    }

    inner class Reply2ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        val reply2_name = itemView.findViewById<TextView>(R.id.reply2_name)
        val reply2_date = itemView.findViewById<TextView>(R.id.reply2_date)
        val reply2 = itemView.findViewById<TextView>(R.id.reply2)
        //val reply_image = itemView.findViewById<ImageView>(R.id.reply_image)

    }

}