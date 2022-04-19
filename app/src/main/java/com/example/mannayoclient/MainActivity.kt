package com.example.mannayoclient

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.example.mannayoclient.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.button6.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
        }

        binding.radioGroup.setOnCheckedChangeListener {radioGroup, checkedId ->
            when(checkedId) {


                R.id.nickname -> {binding.editphone.hint = "닉네임을 입력해주세요."
                    binding.editphone.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL}
                R.id.phone -> {binding.editphone.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"
                    binding.editphone.inputType = InputType.TYPE_CLASS_PHONE}

            }
        }
    }
}
