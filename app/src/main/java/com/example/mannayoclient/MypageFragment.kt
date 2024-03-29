package com.example.mannayoclient

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mannayoclient.databinding.MypageFragBinding
import com.example.mannayoclient.dto.ReceiveOK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment(R.layout.mypage_frag) {
    lateinit var binding: MypageFragBinding
    lateinit var activity: MypageActivity
    lateinit var memberimage: Bitmap
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = MypageFragBinding.bind(view)

        activity = context as MypageActivity

        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val editor = shared.edit()
        val memberid = shared.getString("id", null)?.toLong()

//        val imageUrl = "https://t1.daumcdn.net/cfile/tistory/1342A5564DB8D02102"
//        Glide.with(this).load(imageUrl).into(binding.imageView81)

        retrofitService.service.getMyProfileImage(memberid).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val receive = response.body()?.byteStream()
                coroutineScope.launch {
                    val originalDeferred =
                        coroutineScope.async(Dispatchers.IO) {
                            BitmapFactory.decodeStream(receive)
                        }
                    memberimage = originalDeferred.await()
                    binding.imageView81.setImageBitmap(memberimage)
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        binding.information.setOnClickListener {
            findNavController().navigate(R.id.action_mypageFragment_to_mypage2Fragment)
        }


        binding.member.setOnClickListener {
            startActivity(Intent(requireContext(), CancelMemberActivity::class.java))
        }

        binding.nickname.text = "nickname"


        binding.textView61.setOnClickListener {
            retrofitService.service.signout(memberid).enqueue(object : Callback<ReceiveOK> {
                override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                    val receive = response.body() as ReceiveOK
                    if (response.isSuccessful) {
                        editor.putString("email", null)
                        editor.putString("password", null)
                        editor.putString("id", null)
                        editor.putString("nickname", null)
                        editor.commit()
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        Toast.makeText(activity, receive.response, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                }
            })

        }

    }
}