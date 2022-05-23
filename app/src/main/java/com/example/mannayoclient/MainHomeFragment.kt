package com.example.mannayoclient

import android.content.Context
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
        val nickname = shared.getString("nickname", "null")
        val id = shared.getString("id", "0")?.toLong()
        if (nickname.equals("null")) {
            activity.FragmentView()
        }

        binding.Hansik.setOnClickListener() {
            bundle.putString("categorization", "HANSIK")
            findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )
        }

        binding.Bunsik.setOnClickListener() {
            bundle.putString("categorization", "BUNSIK")
            findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )
        }

        binding.Japanese.setOnClickListener() {
            bundle.putString("categorization", "ILSIK")
            findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )
        }

        binding.Chinese.setOnClickListener() {
            bundle.putString("categorization", "JUNGSIK")
            findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )
        }

        binding.Western.setOnClickListener() {
            bundle.putString("categorization", "YANGSIK")
            findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )
        }

        binding.Fast.setOnClickListener() {
            bundle.putString("categorization", "FASTFOOD")
            findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )
        }

        binding.Dessert.setOnClickListener() {
            bundle.putString("categorization", "CAFE_DESSERT")
            findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )
        }

        binding.Beer.setOnClickListener() {
            bundle.putString("categorization", "SULJIP")
            findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )
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

    }

}