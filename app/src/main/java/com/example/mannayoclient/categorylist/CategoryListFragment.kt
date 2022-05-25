package com.example.mannayoclient.categorylist

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.*
import com.example.mannayoclient.databinding.CategoryItemBinding
import com.example.mannayoclient.databinding.CategoryListFragBinding
import com.example.mannayoclient.storereviewlist.StoreReviewModel
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.time.LocalDateTime
import java.time.LocalTime


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

        binding.review.setOnClickListener {
            binding.review.setImageResource(R.drawable.component_68)
            binding.grade.setImageResource(R.drawable.component_74)
            binding.favorite.setImageResource(R.drawable.component_71)

        }

        binding.grade.setOnClickListener {
            binding.review.setImageResource(R.drawable.component_67)
            binding.grade.setImageResource(R.drawable.component_70)
            binding.favorite.setImageResource(R.drawable.component_71)
        }

        binding.favorite.setOnClickListener {
            binding.review.setImageResource(R.drawable.component_67)
            binding.grade.setImageResource(R.drawable.component_74)
            binding.favorite.setImageResource(R.drawable.component_72)
        }


        val categorization = arguments?.getString("categorization").toString()
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
                findNavController().navigate(R.id.action_categoryListFragment2_to_mainStoreFragment2)
            }

            override fun onHeartClick(view: View, position: Int) {
                edit.putString("restaurantId", items[position].restaurantId.toString())
                edit.commit()
                println("heartclick =" + items[position].Check)
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
                    println("heartclick3 =" + items[position].Check)
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
                                                            it.isJjim
                                                        )
                                                    )
                                                    println("restaurantIsJJim =" + it.isJjim)
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
                                        it.isJjim
                                    )
                                )
                                rv.adapter = rvAdapter
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<List<restaurantInfo>>, t: Throwable) {
                    Log.e("test", "${t.message}")
                }


            })


    }


}

class SortByReview : Comparable<StoreReviewModel>{
    override fun compareTo(other: StoreReviewModel): Int {
        return 0
    }

}


data class restaurantInfo(
    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("type")
    @Expose
    val type: String,

    @SerializedName("starttime")
    @Expose
    val starttime: String,

    @SerializedName("endtime")
    @Expose
    val endtime: String,

    @SerializedName("point")
    @Expose
    val point: Float,

    @SerializedName("address")
    @Expose
    val address: String,

    @SerializedName("imageAddress")
    @Expose
    val imageAddress: String,

    @SerializedName("isJjim")
    @Expose
    val isJjim: Boolean
)

data class restaurantImage(
    @SerializedName("image")
    @Expose
    val image: String
)