package com.example.mannayoclient.mainmenulist

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.MainstoreFragBinding
import com.example.mannayoclient.dto.menu
import com.example.mannayoclient.dto.restaurantDetailInfo
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

class MainStoreFragment : Fragment(R.layout.mainstore_frag) {
    lateinit var binding: MainstoreFragBinding
    lateinit var activity: MainStoreActivity
    val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = MainstoreFragBinding.bind(view)

        activity = context as MainStoreActivity

        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val id = shared.getString("restaurantId", null)?.toLong()
        val check = shared.getString("Jjim", null)?.toBoolean()
        if(check!!) {
            binding.checkBox.isChecked = check
        }

        println(id)
        retrofitService.service.getRestaurantDatailInfo(id)
            .enqueue(object : Callback<restaurantDetailInfo> {
                override fun onResponse(
                    call: Call<restaurantDetailInfo>,
                    response: Response<restaurantDetailInfo>
                ) {
                    val receive = response.body() as restaurantDetailInfo
                    if (response.isSuccessful) {
                        binding.restaurantName.text = receive.name
                        binding.textView34.text = receive.starPoint.toString()
                        binding.restaurantAddress.text = receive.address
                        binding.restaurantTime.text = receive.start_time + " ~ " + receive.end_time
                        binding.restaurantNumber.text = receive.number
                    }
                }

                override fun onFailure(call: Call<restaurantDetailInfo>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        val rv: RecyclerView = binding.mainmenurv

        val items = ArrayList<MainMenuModel>()
        val rvAdapter = MainMenuRVAdapter(items)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        retrofitService.service.getMenu(id).enqueue(object : Callback<List<menu>> {
            override fun onResponse(call: Call<List<menu>>, response: Response<List<menu>>) {
                val receive = response.body() as List<menu>
                for(i in receive) {
                    retrofitService.service.getMenuImage(i.id).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            val image = response.body()?.byteStream()
                            if(!i.image.isNullOrEmpty()) {
                                coroutineScope.launch {
                                    val deferimage = coroutineScope.async(Dispatchers.IO) {
                                        BitmapFactory.decodeStream(image)
                                    }
                                    val bitMenuImage = deferimage.await()
                                    items.add(
                                        MainMenuModel(
                                            "${i.name}\n\n${i.price}원",
                                            bitMenuImage
                                        )
                                    )
                                    rvAdapter.notifyDataSetChanged()
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        }

                    })
                }
            }

            override fun onFailure(call: Call<List<menu>>, t: Throwable) {

            }

        })


        //메뉴
        binding.menuButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainStoreFragment_to_storeMenuFragment)
        }

        //리뷰
        binding.reviewButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainStoreFragment_to_storeReviewFragment)
        }


    }
}