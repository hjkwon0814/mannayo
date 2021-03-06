package com.example.mannayoclient.categorylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.IdFragment
import com.example.mannayoclient.PwFragment
import com.example.mannayoclient.R

class CategoryRVAdapter(private val items : List<CategoryModel>) : RecyclerView.Adapter<CategoryRVAdapter.Viewholder>(){

    interface ItemClick {
        fun onClick(view : View, position: Int)
        fun onHeartClick(view :View, position: Int)
    }
    var itemClick : ItemClick? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        if (itemClick != null) {
            holder.itemView.setOnClickListener{
                v -> itemClick?.onClick(v, position)
            }
            holder.itemView.findViewById<CheckBox>(R.id.heartArea).setOnClickListener {
               v -> itemClick?.onHeartClick(v, position)
            }
        }
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item: CategoryModel) {

            val category_title = itemView.findViewById<TextView>(R.id.titleArea)
            category_title.text = item.title

            val category_detail = itemView.findViewById<TextView>(R.id.detailArea)
            category_detail.text = item.detail

            val category_opentime = itemView.findViewById<TextView>(R.id.timeArea)
            category_opentime.text = item.open_time

            val category_grade = itemView.findViewById<TextView>(R.id.gradeArea)
            category_grade.text = item.grade

            val category_image= itemView.findViewById<ImageView>(R.id.imageArea)
            category_image.setImageBitmap(item.image)

            val restId = item.restaurantId

            val checking = itemView.findViewById<CheckBox>(R.id.heartArea)
            checking.isChecked = item.Check

            val countReview = item.countReview

            val countJjim = item.countJjim

        }

    }
}


