package com.example.mannayoclient.categorylist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.SecondActivity
import com.example.mannayoclient.databinding.CategoryItemBinding
import com.example.mannayoclient.databinding.CategoryListFragBinding
import com.example.mannayoclient.mannayoService
import com.example.mannayoclient.retrofitService
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
    lateinit var activity: SecondActivity
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = CategoryListFragBinding.bind(view)

        activity = context as SecondActivity


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
                findNavController().navigate(R.id.action_categoryListFragment_to_mainStoreFragment)
            }
        }

        retrofitService.service.getRestaurantList(categorization)
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
                                                            originalBitmap
                                                        )
                                                    )
                                                    rv.adapter = rvAdapter
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
                                        bitmap
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
    val imageAddress: String
)

data class restaurantImage(
    @SerializedName("image")
    @Expose
    val image: String
)