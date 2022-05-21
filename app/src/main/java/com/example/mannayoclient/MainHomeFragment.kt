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

        val bundle = Bundle()


        binding.Hansik.setOnClickListener(){
            bundle.putString("categorization", "HANSIK")
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment, bundle)
        }

        binding.Bunsik.setOnClickListener(){
            bundle.putString("categorization", "BUNSIK")
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment, bundle)
    }

        binding.Japanese.setOnClickListener(){
            bundle.putString("categorization", "ILSIK")
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment, bundle)
        }

        binding.Chinese.setOnClickListener(){
            bundle.putString("categorization", "JUNGSIK")
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment, bundle)
        }

        binding.Western.setOnClickListener(){
            bundle.putString("categorization", "YANGSIK")
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment, bundle)
        }

        binding.Fast.setOnClickListener(){
            bundle.putString("categorization", "FASTFOOD")
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment, bundle)
        }

        binding.Dessert.setOnClickListener(){
            bundle.putString("categorization", "CAFE_DESSERT")
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment, bundle)
        }

        binding.Beer.setOnClickListener(){
            bundle.putString("categorization", "SULJIP")
            findNavController().navigate(R.id.action_mainHomeFragment_to_categoryListFragment, bundle)
        }


        binding.heart.setOnClickListener(){
            findNavController().navigate(R.id.action_mainHomeFragment_to_favoritistFragment)
        }





    }
}