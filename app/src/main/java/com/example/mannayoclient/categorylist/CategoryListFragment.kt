package com.example.mannayoclient.categorylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.CategoryListBinding


class CategoryListFragment : Fragment(R.layout.category_list) {
    lateinit var binding: CategoryListBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = CategoryListBinding.bind(view)

        val rv : RecyclerView = binding.rv

        val items = ArrayList<CategoryModel>()

        val rvAdapter = CategoryRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())
    }
}