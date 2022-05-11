package com.example.mannayoclient

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.CategoryListFragBinding
import com.example.mannayoclient.databinding.MainhomeFragBinding

class MainHomeFragment : Fragment(R.layout.mainhome_frag) {
    lateinit var binding: MainhomeFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = MainhomeFragBinding.bind(view)



        binding.Hansik.setOnClickListener(){
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment)
        }

        binding.Bunsik.setOnClickListener(){
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment)
        }

        binding.Japanese.setOnClickListener(){
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment)
        }

        binding.Chinese.setOnClickListener(){
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment)
        }

        binding.Western.setOnClickListener(){
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment)
        }

        binding.Fast.setOnClickListener(){
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment)
        }

        binding.Dessert.setOnClickListener(){
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment)
        }

        binding.Beer.setOnClickListener(){
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment)
        }





    }
}