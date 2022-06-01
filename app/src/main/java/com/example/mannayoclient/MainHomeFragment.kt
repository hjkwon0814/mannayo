package com.example.mannayoclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.advertiselist.AdvertiseActivity
import com.example.mannayoclient.alarmlist.AlarmActivity
import com.example.mannayoclient.categorylist.CategoryActivity
import com.example.mannayoclient.categorylist.CategoryListFragment
import com.example.mannayoclient.databinding.MainhomeFragBinding
import com.example.mannayoclient.dto.ReceiveOK
import com.example.mannayoclient.todaylist.TodayActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainHomeFragment : Fragment(R.layout.mainhome_frag) {
    lateinit var binding: MainhomeFragBinding
    lateinit var activity: SecondActivity
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = MainhomeFragBinding.bind(view)

        activity = context as SecondActivity

        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val edit = shared.edit()
        edit.putString("Genesis","Genesis")
        edit.commit()
        val nickname = shared.getString("nickname", "null")
        val id = shared.getString("id", "0")?.toLong()
        val token = shared.getString("FCMtoken", null)

        retrofitService.service.setFCMtoken(id, token).enqueue(object : Callback<ReceiveOK> {
            override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
            }

            override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {
            }
        })

        if (nickname.equals("null")) {
            activity.FragmentView()
        }

        binding.Hansik.setOnClickListener() {

            edit.putString("categorization", "HANSIK")
            edit.commit()
            startActivity(Intent(requireContext(), CategoryActivity::class.java))
        }

        binding.Bunsik.setOnClickListener() {
            edit.putString("categorization", "BUNSIK")
            edit.commit()
            startActivity(Intent(requireContext(),CategoryActivity::class.java))
        }

        binding.Japanese.setOnClickListener() {
            edit.putString("categorization", "ILSIK")
            edit.commit()
            startActivity(Intent(requireContext(),CategoryActivity::class.java))

        }

        binding.Chinese.setOnClickListener() {
            edit.putString("categorization", "JUNGSIK")
            edit.commit()
            startActivity(Intent(requireContext(),CategoryActivity::class.java))

        }

        binding.Western.setOnClickListener() {
            edit.putString("categorization", "YANGSIK")
            edit.commit()
            startActivity(Intent(requireContext(),CategoryActivity::class.java))

        }

        binding.Fast.setOnClickListener() {
            edit.putString("categorization", "FASTFOOD")
            edit.commit()
            startActivity(Intent(requireContext(),CategoryActivity::class.java))

        }

        binding.Dessert.setOnClickListener() {
            edit.putString("categorization", "CAFE_DESSERT")
            edit.commit()
            startActivity(Intent(requireContext(),CategoryActivity::class.java))

        }

        binding.Beer.setOnClickListener() {
            edit.putString("categorization", "SULJIP")
            edit.commit()
            startActivity(Intent(requireContext(),CategoryActivity::class.java))
        }


        binding.heart.setOnClickListener() {
            startActivity(Intent(requireContext(),FavoritistActivity::class.java))
        }

        binding.board.setOnClickListener() {
            startActivity(Intent(requireContext(),AdvertiseActivity::class.java))
        }

        binding.today.setOnClickListener() {
            startActivity(Intent(requireContext(),TodayActivity::class.java))
        }


        binding.alarm.setOnClickListener() {
            startActivity(Intent(requireContext(),AlarmActivity::class.java))
        }

        binding.map.setOnClickListener() {
//            findNavController().navigate(R.id.action_mainHomeFragment_to_mapFragment)
            startActivity(Intent(requireContext(),MapActivity::class.java))
        }

        binding.mypage.setOnClickListener() {
            startActivity(Intent(requireContext(),MypageActivity::class.java))
        }

    }

}