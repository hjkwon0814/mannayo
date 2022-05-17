package com.example.mannayoclient

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.SearchFragBinding

class SearchFragment : Fragment(R.layout.search_frag) {
    lateinit var binding: SearchFragBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = SearchFragBinding.bind(view)

        binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })



    }
}