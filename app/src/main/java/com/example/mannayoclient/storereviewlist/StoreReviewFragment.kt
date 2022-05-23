package com.example.mannayoclient.storereviewlist

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.SecondActivity
import com.example.mannayoclient.categorylist.CategoryModel
import com.example.mannayoclient.databinding.StorereviewFragBinding
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

class StoreReviewFragment : Fragment(R.layout.storereview_frag) {
    lateinit var binding: StorereviewFragBinding
    lateinit var activity : SecondActivity
    lateinit var image : Bitmap
    lateinit var memberimage: Bitmap
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = StorereviewFragBinding.bind(view)

        activity = context as SecondActivity
        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)

        val rv: RecyclerView = binding.recyclerView

        val items = ArrayList<StoreReviewModel>()
        val restauratId = shared.getString("restaurantId",null)?.toLong()

        retrofitService.service.getReviewList(restauratId).enqueue(object : Callback<List<ReviewList>> {
            override fun onResponse(
                call: Call<List<ReviewList>>,
                response: Response<List<ReviewList>>
            ) {
                val receive = response.body() as List<ReviewList>
                if (response.isSuccessful) {
                    for(i in receive) {
                        if(!i.image.isNullOrEmpty()) {
                            retrofitService.service.getReviewImage(i.id).enqueue(object :Callback<ResponseBody> {
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
                                        image = originalDeferred.await()
                                    }
                                }
                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                                }

                            })
                        }else {
                            image = BitmapFactory.decodeResource(
                                resources,
                                R.drawable.component_101
                            )
                        }

                        if(!i.memberImage.isNullOrEmpty()) {
                            retrofitService.service.getMyProfileImage(i.memberId).enqueue(object :Callback<ResponseBody> {
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
                                        memberimage = originalDeferred.await()
                                    }
                                }
                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                                }

                            })
                        }else{
                            memberimage = BitmapFactory.decodeResource(
                                resources,
                                R.drawable.component_38
                            )
                        }

                        items.add(StoreReviewModel(i.memberNickname, i.writeDate, i.starPoint.toString(), i.content, image, memberimage))
                    }
                }
            }

            override fun onFailure(call: Call<List<ReviewList>>, t: Throwable) {

            }

        })


        val rvAdapter = StoreReviewRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())

        binding.reviewbutton.setOnClickListener {
            findNavController().navigate(R.id.action_storeReviewFragment_to_reviewWriteFragment)
        }


    }
}

data class ReviewList(
    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("memberId")
    @Expose
    val memberId: Long,

    @SerializedName("content")
    @Expose
    val content: String,

    @SerializedName("writeDate")
    @Expose
    val writeDate: String,

    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("starPoint")
    @Expose
    val starPoint: Float,

    @SerializedName("isDeleted")
    @Expose
    val isDeleted: Boolean,

    @SerializedName("isModified")
    @Expose
    val isModified: Boolean,

    @SerializedName("memberImage")
    @Expose
    val memberImage: String,

    @SerializedName("memberNickname")
    @Expose
    val memberNickname: String
)