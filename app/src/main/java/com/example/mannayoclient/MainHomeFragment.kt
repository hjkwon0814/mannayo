package com.example.mannayoclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.categorylist.CategoryActivity
import com.example.mannayoclient.databinding.MainhomeFragBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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
            startActivity(Intent(requireContext(), CategoryActivity::class.java))
            /*findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )*/
        }

        binding.Bunsik.setOnClickListener() {
            bundle.putString("categorization", "BUNSIK")
            startActivity(Intent(requireContext(), CategoryActivity::class.java))
            /*findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )*/
        }

        binding.Japanese.setOnClickListener() {
            bundle.putString("categorization", "ILSIK")
            startActivity(Intent(requireContext(), CategoryActivity::class.java))
            /*findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )*/
        }

        binding.Chinese.setOnClickListener() {
            bundle.putString("categorization", "JUNGSIK")
            startActivity(Intent(requireContext(), CategoryActivity::class.java))
            /*findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )*/
        }

        binding.Western.setOnClickListener() {
            bundle.putString("categorization", "YANGSIK")
            startActivity(Intent(requireContext(), CategoryActivity::class.java))
            /*findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )*/
        }

        binding.Fast.setOnClickListener() {
            bundle.putString("categorization", "FASTFOOD")
            startActivity(Intent(requireContext(), CategoryActivity::class.java))
            /*findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )*/
        }

        binding.Dessert.setOnClickListener() {
            bundle.putString("categorization", "CAFE_DESSERT")
            startActivity(Intent(requireContext(), CategoryActivity::class.java))
            /*findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )*/
        }

        binding.Beer.setOnClickListener() {
            bundle.putString("categorization", "SULJIP")
            startActivity(Intent(requireContext(), CategoryActivity::class.java))
            /*findNavController().navigate(
                R.id.action_mainHomeFragment_to_categoryListFragment,
                bundle
            )*/
        }


        binding.heart.setOnClickListener() {
            startActivity(Intent(requireContext(),FavoritistActivity::class.java))
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