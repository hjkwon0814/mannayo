package com.example.mannayoclient.mainmenulist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.MainstoreFragBinding

class MainStoreFragment: Fragment(R.layout.mainstore_frag) {
    lateinit var binding: MainstoreFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = MainstoreFragBinding.bind(view)

        val rv : RecyclerView = binding.mainmenurv

        val items = ArrayList<MainMenuModel>()

        //test 용
        items.add(MainMenuModel("a"))
        items.add(MainMenuModel("b"))
        items.add(MainMenuModel("a"))
        items.add(MainMenuModel("b"))


        val rvAdapter = MainMenuRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())

        binding.menuButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainStoreFragment_to_storeMenuFragment)
        }




    }
}

