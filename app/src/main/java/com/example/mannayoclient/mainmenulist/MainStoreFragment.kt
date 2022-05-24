package com.example.mannayoclient.mainmenulist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.SecondActivity
import com.example.mannayoclient.databinding.MainstoreFragBinding
import com.example.mannayoclient.retrofitService
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainStoreFragment: Fragment(R.layout.mainstore_frag) {
    lateinit var binding: MainstoreFragBinding
    lateinit var activity : SecondActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = MainstoreFragBinding.bind(view)

        activity = context as SecondActivity

        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val id = shared.getString("restaurantId", null)?.toLong()

        println(id)
        retrofitService.service.getRestaurantDatailInfo(id).enqueue(object : Callback<restaurantDetailInfo> {
            override fun onResponse(
                call: Call<restaurantDetailInfo>,
                response: Response<restaurantDetailInfo>
            ) {
                val receive = response.body() as restaurantDetailInfo
                if(response.isSuccessful) {
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

        val rv : RecyclerView = binding.mainmenurv

        val items = ArrayList<MainMenuModel>()

        //test 용
        items.add(MainMenuModel("a"))
        items.add(MainMenuModel("b"))
        items.add(MainMenuModel("a"))
        items.add(MainMenuModel("b"))


        val rvAdapter = MainMenuRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())

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

data class restaurantDetailInfo(
    @SerializedName("name")
    @Expose
    val name : String,

    @SerializedName("number")
    @Expose
    val number : String,

    @SerializedName("address")
    @Expose
    val address :String,

    @SerializedName("start_time")
    @Expose
    val start_time : String,

    @SerializedName("end_time")
    @Expose
    val end_time : String,

    @SerializedName("jjimcount")
    @Expose
    val jjimcount : Int,

    @SerializedName("owner")
    @Expose
    val owner : String,

    @SerializedName("starPoint")
    @Expose
    val starPoint : Float
)
