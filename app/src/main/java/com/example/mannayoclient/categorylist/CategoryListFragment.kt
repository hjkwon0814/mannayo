package com.example.mannayoclient.categorylist

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.*
import com.example.mannayoclient.databinding.CategoryListFragBinding
import com.example.mannayoclient.dto.ReceiveOK
import com.example.mannayoclient.dto.restaurantInfo
import com.example.mannayoclient.mainmenulist.MainStoreActivity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryListFragment : Fragment(R.layout.category_list_frag) {
    lateinit var binding: CategoryListFragBinding
    lateinit var activity: CategoryActivity
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = CategoryListFragBinding.bind(view)

        activity = context as CategoryActivity


        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val edit = shared.edit()
        val memberId = shared.getString("id",null)?.toLong()
        edit.putString("Jjim", "false")


        val categorization = shared.getString("categorization", null)
        if(categorization.equals("HANSIK")){
            binding.type.text="한식"
        }else if (categorization.equals("BUNSIK")) {
            binding.type.text="분식"
        }else if (categorization.equals("ILSIK")) {
            binding.type.text="일식"
        } else if (categorization.equals("JUNGSIK")) {
            binding.type.text="중식"
        }else if (categorization.equals("YANGSIK")) {
            binding.type.text="양식"
        }else if (categorization.equals("FASTFOOD")) {
            binding.type.text="패스트푸드"
        }else if (categorization.equals("CAFE_DESSERT")) {
            binding.type.text="디저트"
        }else if (categorization.equals("SULJIP")) {
            binding.type.text="술집"
        }
        val items = ArrayList<CategoryModel>()


        val rv: RecyclerView = binding.rv
        val rvAdapter = CategoryRVAdapter(items)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())
        rvAdapter.itemClick = object : CategoryRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                edit.putString("restaurantId", items[position].restaurantId.toString())
                edit.putString("Jjim", items[position].Check.toString())
                edit.commit()
                startActivity(Intent(requireContext(), MainStoreActivity::class.java))
            }

            override fun onHeartClick(view: View, position: Int) {
                edit.putString("restaurantId", items[position].restaurantId.toString())
                edit.commit()
                if(items[position].Check) {// 체크 박스 확인 true
                    items[position].Check = false
                    println("heartclick2 =" + items[position].Check)
                    retrofitService.service.deleteJjim(shared.getString("id",null)?.toLong(),shared.getString("restaurantId", null)?.toLong()).enqueue(object : Callback<ReceiveOK> {
                        override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                            val receive = response.body() as ReceiveOK
                            if(response.isSuccessful && receive.success) {
                                Toast.makeText(activity,"찜 등록 해제 되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                        }

                    })
                }
                else if(!items[position].Check) { //false
                    items[position].Check = true
                    retrofitService.service.setJjim(shared.getString("id",null)?.toLong(),shared.getString("restaurantId", null)?.toLong()).enqueue(object : Callback<ReceiveOK> {
                        override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                            val receive = response.body() as ReceiveOK
                            if(response.isSuccessful && receive.success) {
                                Toast.makeText(activity,"찜 등록 되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                        }
                    })

                }

            }

        }


        retrofitService.service.getRestaurantList(categorization,memberId)
            .enqueue(object : Callback<List<restaurantInfo>> {
                override fun onResponse(
                    call: Call<List<restaurantInfo>>,
                    response: Response<List<restaurantInfo>>
                ) {
                    val receive = response.body() as List<restaurantInfo>
                    if (response.isSuccessful) {
                        for (it in receive) {
                            if (!it.imageAddress.equals("")) {
                                retrofitService.service.getRestaurantImage(it.id)
                                    .enqueue(object : Callback<ResponseBody> {
                                        override fun onResponse(
                                            call: Call<ResponseBody>,
                                            response: Response<ResponseBody>
                                        ) {
                                            val receiveimage = response.body()?.byteStream()
                                            if (response.isSuccessful) {
                                                coroutineScope.launch {
                                                    val originalDeferred =
                                                        coroutineScope.async(Dispatchers.IO) {
                                                            BitmapFactory.decodeStream(receiveimage)
                                                        }
                                                    val originalBitmap = originalDeferred.await()
                                                    items.add(
                                                        CategoryModel(
                                                            it.name,
                                                            it.address,
                                                            it.starttime + "~" + it.endtime,
                                                            it.point.toString(),
                                                            originalBitmap,
                                                            it.id,
                                                            it.isJjim,
                                                            it.countReview,
                                                            it.countJjim
                                                        )
                                                    )
                                                    items.sortByDescending { it.countReview }
                                                    rvAdapter.notifyDataSetChanged()
                                                }
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<ResponseBody>,
                                            t: Throwable
                                        ) {
                                            Log.e("imageTest", "${t.message}")
                                        }

                                    })
                            } else {
                                val bitmap = BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.component_101
                                )
                                items.add(
                                    CategoryModel(
                                        it.name,
                                        it.address,
                                        it.starttime + "~" + it.endtime,
                                        it.point.toString(),
                                        bitmap,
                                        it.id,
                                        it.isJjim,
                                        it.countReview,
                                        it.countJjim
                                    )
                                )
                                items.sortBy { it.countReview }
                                rvAdapter.notifyDataSetChanged()
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<List<restaurantInfo>>, t: Throwable) {
                    Log.e("test", "${t.message}")
                }


            })



        binding.review.setOnClickListener {
            items.sortByDescending { it.countReview}
            rv.adapter = rvAdapter
            binding.review.setImageResource(R.drawable.component_68)
            binding.grade.setImageResource(R.drawable.component_74)
            binding.favorite.setImageResource(R.drawable.component_71)

        }

        binding.grade.setOnClickListener {
            items.sortByDescending { it.grade }
            rv.adapter = rvAdapter
            binding.review.setImageResource(R.drawable.component_67)
            binding.grade.setImageResource(R.drawable.component_70)
            binding.favorite.setImageResource(R.drawable.component_71)
        }

        binding.favorite.setOnClickListener {
            items.sortByDescending { it.countJjim }
            rv.adapter = rvAdapter
            binding.review.setImageResource(R.drawable.component_67)
            binding.grade.setImageResource(R.drawable.component_74)
            binding.favorite.setImageResource(R.drawable.component_72)
        }
    }


}
