/*package com.example.mannayoclient.favoritist

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.SecondActivity
import com.example.mannayoclient.categorylist.CategoryModel
import com.example.mannayoclient.categorylist.CategoryRVAdapter
import com.example.mannayoclient.categorylist.restaurantInfo
import com.example.mannayoclient.databinding.CategoryListFragBinding
import com.example.mannayoclient.databinding.FavoritistFragBinding
import com.example.mannayoclient.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class favoritistFragment : Fragment(R.layout.favoritist_frag) {
    lateinit var binding: FavoritistFragBinding
    lateinit var activity : SecondActivity

    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FavoritistFragBinding.bind(view)


        val items = ArrayList<CategoryModel>()

        activity = context as SecondActivity

        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val memberId = shared.getString("id",null)?.toLong()

        val rv: RecyclerView = binding.fRv
        val rvAdapter = CategoryRVAdapter(items)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())
        rvAdapter.itemClick = object : CategoryRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //findNavController().navigate(R.id.action_favoritistFragment_to_mainStoreFragment)
            }

            override fun onHeartClick(view: View, position: Int) {
                TODO("Not yet implemented")
            }
        }


        retrofitService.service.getRestaurantJjimList(memberId)
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
                                        it.isJjim,
                                        it.countReview,
                                        it.countJjim
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
}*/