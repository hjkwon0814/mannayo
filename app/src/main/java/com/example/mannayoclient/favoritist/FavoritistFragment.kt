package com.example.mannayoclient.favoritist

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.categorylist.CategoryModel
import com.example.mannayoclient.categorylist.CategoryRVAdapter
import com.example.mannayoclient.categorylist.restaurantInfo
import com.example.mannayoclient.databinding.CategoryListFragBinding
import com.example.mannayoclient.databinding.FavoritistFragBinding
import com.example.mannayoclient.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class favoritistFragment: Fragment(R.layout.favoritist_frag) {
    lateinit var binding: FavoritistFragBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FavoritistFragBinding.bind(view)


        val items = ArrayList<CategoryModel>()


        val rv: RecyclerView = binding.fRv
        val rvAdapter = CategoryRVAdapter(items)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())
        rvAdapter.itemClick = object : CategoryRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                findNavController().navigate(R.id.action_favoritistFragment_to_mainStoreFragment)
            }
        }


    }
}