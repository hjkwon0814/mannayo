package com.example.mannayoclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import com.example.mannayoclient.databinding.ActivityMainBinding
import com.example.mannayoclient.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {

        lateinit var binding: ActivityPasswordBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivityPasswordBinding.inflate(layoutInflater)

            setContentView(binding.root)

            binding.button5.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }


            binding.radioGroup.setOnCheckedChangeListener {radioGroup, checkedId ->
                when(checkedId) {


                    R.id.email -> {binding.editphone.hint = "이메일을 입력하세요."
                        binding.editphone.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS}
                    R.id.phone -> {binding.editphone.hint = "휴대폰번호를 입력해주세요.(‘-’제외)"
                        binding.editphone.inputType = InputType.TYPE_CLASS_PHONE}



                }
            }
    }
}