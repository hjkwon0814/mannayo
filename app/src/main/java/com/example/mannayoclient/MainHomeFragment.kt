package com.example.mannayoclient

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.MainhomeFragBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
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

        val bundle = Bundle()
        activity = context as SecondActivity

        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val edit = shared.edit()
        val nickname = shared.getString("nickname", "null")
        val id = shared.getString("id", "0")?.toLong()
        if (nickname.equals("null")) {
            activity.FragmentView()
        }

        binding.Hansik.setOnClickListener() {
            edit.putString("categorization", "HANSIK")
            edit.commit()
            startActivity(Intent(requireContext(),CategoryActivity::class.java))
        }

        binding.Bunsik.setOnClickListener() {
            edit.putString("categorizion", "BUNSIK")
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
            findNavController().navigate(R.id.action_mainHomeFragment_to_favoritistFragment)
        }

        binding.board.setOnClickListener() {
            findNavController().navigate(R.id.action_mainHomeFragment_to_advertiseFragment)
        }

        binding.today.setOnClickListener() {
            findNavController().navigate(R.id.action_mainHomeFragment_to_todayFragment)
        }


        binding.alarm.setOnClickListener() {
            findNavController().navigate(R.id.action_mainHomeFragment_to_alarmFragment)
        }

        binding.map.setOnClickListener() {
            findNavController().navigate(R.id.action_mainHomeFragment_to_mapFragment)
        }

        binding.mypage.setOnClickListener() {
            findNavController().navigate(R.id.action_mainHomeFragment_to_mypageFragment)
        }

    }

}