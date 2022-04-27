package com.example.mannayoclient

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mannayoclient.databinding.Pw2FragBinding
import androidx.navigation.fragment.findNavController

class PwFragment2 : Fragment(R.layout.pw2_frag) {
    lateinit var binding: Pw2FragBinding


    lateinit var mainActivity: MainActivity



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = Pw2FragBinding.bind(view)


        /*웨젯 pwComment 특정 문자열 색상, 글꼴 바꾸기
        val text = "새 비밀번호를 설정해주세요.\n다른 아이디나 사이트에서 사용한 적 없는\n 안전한 비밀번호로 변경해주세요."
        val spannableStringBuilder = SpannableStringBuilder(text)
        val endIndex = 14
        val startIndex = 0

        spannableStringBuilder.setSpan(
            ForegroundColorSpan(Color.parseColor("#191919")), startIndex, endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        binding.pwComment.text=spannableStringBuilder
    */



        binding.idbutton.setOnClickListener {
            mainActivity.onFragmentChange(0)
        }


    }

}