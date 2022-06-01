package com.example.mannayoclient.alarmlist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.AlarmFragBinding
import com.example.mannayoclient.dto.NoticeDto
import com.example.mannayoclient.dto.ReceiveOK
import com.example.mannayoclient.retrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmFragment : Fragment(R.layout.alarm_frag) {
    lateinit var binding: AlarmFragBinding
    lateinit var activity: AlarmActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = AlarmFragBinding.bind(view)
        activity = context as AlarmActivity

        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val memberid = shared.getString("id", null)?.toLong()

        val rv: RecyclerView = binding.rv
        val items = ArrayList<AlarmModel>()
        val rvAdapter = AlarmRVAdapter(items)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())


        retrofitService.service.getNoticeAll(memberid).enqueue(object : Callback<List<NoticeDto>> {
            override fun onResponse(call: Call<List<NoticeDto>>, response: Response<List<NoticeDto>>) {
                val receive = response.body() as List<NoticeDto>
                if(response.isSuccessful) {
                    for(n in receive) {
                        items.add(AlarmModel(n.title,n.contents,n.date))
                        rvAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<NoticeDto>>, t: Throwable) {
            }
        })

        binding.alarmImg.setOnClickListener {
            binding.alarmImg.setImageResource(R.drawable.alarm)
            binding.messageImg.setImageResource(R.drawable.message)
            retrofitService.service.getNoticeAll(memberid).enqueue(object : Callback<List<NoticeDto>> {
                override fun onResponse(call: Call<List<NoticeDto>>, response: Response<List<NoticeDto>>) {
                    val receive = response.body() as List<NoticeDto>
                    if(response.isSuccessful) {
                        for(n in receive) {
                            items.add(AlarmModel(n.title,n.contents,n.date))
                            rvAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<List<NoticeDto>>, t: Throwable) {
                }
            })
        }

        binding.messageImg.setOnClickListener {
            binding.alarmImg.setImageResource(R.drawable.alarm2)
            binding.messageImg.setImageResource(R.drawable.message2)
            retrofitService.service.getNoticeAll(memberid).enqueue(object : Callback<List<NoticeDto>> {
                override fun onResponse(call: Call<List<NoticeDto>>, response: Response<List<NoticeDto>>) {
                    val receive = response.body() as List<NoticeDto>
                    if(response.isSuccessful) {
                        for(n in receive) {
                            items.add(AlarmModel(n.title,n.contents,n.date))
                            rvAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<List<NoticeDto>>, t: Throwable) {
                }
            })
        }


    }
}
