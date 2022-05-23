package com.example.mannayoclient.storereviewlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.StorereviewFragBinding
import com.example.mannayoclient.retrofitService
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreReviewFragment : Fragment(R.layout.storereview_frag) {
    lateinit var binding: StorereviewFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = StorereviewFragBinding.bind(view)

        val rv: RecyclerView = binding.recyclerView

        val items = ArrayList<StoreReviewModel>()

        items.add(StoreReviewModel("a","b","c","d"))

        retrofitService.service.getReviewList().enqueue(object : Callback<ReviewList> {
            override fun onResponse(call: Call<ReviewList>, response: Response<ReviewList>) {

            }

            override fun onFailure(call: Call<ReviewList>, t: Throwable) {

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
)