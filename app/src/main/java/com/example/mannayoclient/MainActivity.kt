package com.example.mannayoclient

import android.os.Bundle
import android.text.InputType
import android.text.InputType.TYPE_CLASS_TEXT
import androidx.appcompat.app.AppCompatActivity
import com.example.mannayoclient.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.radioGroup.setOnCheckedChangeListener {radioGroup, checkedId ->
            when(checkedId) {


                R.id.email -> binding.editphone.hint = "이메일을 입력하세요."
                //R.id.email -> binding.editphone. inputType =TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                //키보드 바꾸기..
                R.id.phone -> binding.editphone.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"

            }
        }
    }
}
