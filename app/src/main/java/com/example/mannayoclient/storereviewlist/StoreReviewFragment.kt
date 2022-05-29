package com.example.mannayoclient.storereviewlist

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.ReviewWriteActivity
import com.example.mannayoclient.SecondActivity
import com.example.mannayoclient.databinding.StorereviewFragBinding
import com.example.mannayoclient.dto.ReviewList
import com.example.mannayoclient.mainmenulist.MainStoreActivity
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
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class StoreReviewFragment : Fragment(R.layout.storereview_frag) {
    lateinit var binding: StorereviewFragBinding
    lateinit var activity: MainStoreActivity
    lateinit var memberimage: Bitmap
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = StorereviewFragBinding.bind(view)

        activity = context as MainStoreActivity
        memberimage = BitmapFactory.decodeResource(
            resources,
            R.drawable.component_101
        )

        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)

        val rv: RecyclerView = binding.recyclerView

        val items = ArrayList<StoreReviewModel>()
        val restaurantId = shared.getString("restaurantId", null)?.toLong()

        val rvAdapter = StoreReviewRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())

        binding.reviewbutton.setOnClickListener {
             onActivityChange()
        }
        retrofitService.service.getReviewList(restaurantId)
            .enqueue(object : Callback<List<ReviewList>> {
                override fun onResponse(
                    call: Call<List<ReviewList>>,
                    response: Response<List<ReviewList>>
                ) {
                    val receive = response.body() as List<ReviewList>
                    println(receive)
                    if (response.isSuccessful) {
                        for (i in receive) {
                            println("c")
                            if (!i.image.isNullOrEmpty()) {
                                retrofitService.service.getReviewImage(i.id)
                                    .enqueue(object : Callback<ResponseBody> {
                                        override fun onResponse(
                                            call: Call<ResponseBody>,
                                            response: Response<ResponseBody>
                                        ) {
                                            val receiveimage = response.body()?.byteStream()
                                            coroutineScope.launch {
                                                val originalDeferred =
                                                    coroutineScope.async(Dispatchers.IO) {
                                                        BitmapFactory.decodeStream(receiveimage)
                                                    }
                                                val image = originalDeferred.await()
                                                items.add(
                                                    StoreReviewModel(
                                                        i.memberNickname,
                                                        i.writeDate,
                                                        i.starPoint.toString(),
                                                        i.content,
                                                        image
                                                    )
                                                )
                                                items.sortByDescending { i.writeDate }
                                                rv.adapter = rvAdapter
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<ResponseBody>,
                                            t: Throwable
                                        ) {

                                        }
                                    })
                            }else {
                                items.add(
                                    StoreReviewModel(
                                        i.memberNickname,
                                        i.writeDate,
                                        i.starPoint.toString(),
                                        i.content,
                                        memberimage
                                    )
                                )
                                items.sortByDescending { i.writeDate }
                                rv.adapter = rvAdapter
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ReviewList>>, t: Throwable) {
                }
            })

        binding.reviewbutton.setOnClickListener {
            startActivity(Intent(requireContext(), ReviewWriteActivity::class.java))
        }
    }

    fun onActivityChange() {
        val intent = Intent(activity, ReviewWriteActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}
