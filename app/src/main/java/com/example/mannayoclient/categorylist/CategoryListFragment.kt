package com.example.mannayoclient.categorylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.CategoryListFragBinding


class CategoryListFragment : Fragment(R.layout.category_list_frag) {
    lateinit var binding: CategoryListFragBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = CategoryListFragBinding.bind(view)

        val rv : RecyclerView = binding.rv

        val items = ArrayList<CategoryModel>()

        //test 용
        items.add(CategoryModel("a","a","a","a"))
        items.add(CategoryModel("b","b","b","b"))
        items.add(CategoryModel("b","b","b","b"))
        items.add(CategoryModel("b","b","b","b"))

        val rvAdapter = CategoryRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())

        rvAdapter.itemClick = object : CategoryRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                findNavController().navigate(R.id.action_categoryListFragment_to_mainStoreFragment)
            }

        }



    }
}