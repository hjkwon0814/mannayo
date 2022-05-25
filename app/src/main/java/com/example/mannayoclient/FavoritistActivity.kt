package com.example.mannayoclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.categorylist.CategoryModel
import com.example.mannayoclient.categorylist.CategoryRVAdapter
import com.example.mannayoclient.databinding.ActivityFavoritistBinding

class FavoritistActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoritistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityFavoritistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = ArrayList<CategoryModel>()


        val rv: RecyclerView = binding.fRv
        val rvAdapter = CategoryRVAdapter(items)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)
        rvAdapter.itemClick = object : CategoryRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //findNavController().navigate(R.id.action_favoritistFragment_to_mainStoreFragment)
            }
        }

    }

}