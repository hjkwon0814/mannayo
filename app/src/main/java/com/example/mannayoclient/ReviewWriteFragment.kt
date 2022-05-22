package com.example.mannayoclient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mannayoclient.databinding.ReviewwriteFragBinding
import com.example.mannayoclient.databinding.SearchFragBinding

class ReviewWriteFragment : Fragment(R.layout.reviewwrite_frag) {
    lateinit var binding: ReviewwriteFragBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding =  ReviewwriteFragBinding.bind(view)




    }
}