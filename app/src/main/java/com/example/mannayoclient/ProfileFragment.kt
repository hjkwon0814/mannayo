package com.example.mannayoclient

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.ProfileFragBinding


class ProfileFragment : Fragment(R.layout.profile_frag) {
    lateinit var binding: ProfileFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = ProfileFragBinding.bind(view)




        //완료누르면 메인홈으로
        binding.completion.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_mainHomeFragment)
        }


        //닉네임 글자수에 따라 밑에 텍스트 바꾸기
        binding.editTextTextPersonName2.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.textView13.text = "0/7"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userinput = binding.editTextTextPersonName2.text.toString()
                binding.textView13.text = userinput.length.toString() + "/7"
            }

            override fun afterTextChanged(s: Editable?) {
                var userinput = binding.editTextTextPersonName2.text.toString()
                binding.textView13.text = userinput.length.toString() + "/7"
            }

        })



    }
}