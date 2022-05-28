package com.example.mannayoclient.advertiselist

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.writelist.WriteActivity
import com.example.mannayoclient.databinding.AdvertiseFragBinding
import com.example.mannayoclient.dto.BoardResponseDto
import com.example.mannayoclient.retrofitService
import com.example.mannayoclient.todaylist.TodayActivity
import com.example.mannayoclient.todaylist.TodayModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdvertiseFragment : Fragment(R.layout.advertise_frag) {
    lateinit var binding: AdvertiseFragBinding
    lateinit var activity: AdvertiseActivity
    val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = AdvertiseFragBinding.bind(view)

        activity = context as AdvertiseActivity
        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val edit = shared.edit()
        edit.putString("boardtype","ADVERTISE_BOARD")
        edit.commit()

        val type = shared.getString("boardtype", null)

        val rv: RecyclerView = binding.rv
        val items = ArrayList<AdvertiseModel>()
        val rvAdapter = AdvertiseRVAdapter(items)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())
        rvAdapter.itemClick = object : AdvertiseRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                findNavController().navigate(R.id.action_advertiseFragment_to_advertiseDetailFragment)
            }
        }

        retrofitService.service.getBoardList(type).enqueue(object :
            Callback<List<BoardResponseDto>> {
            override fun onResponse(
                call: Call<List<BoardResponseDto>>,
                response: Response<List<BoardResponseDto>>
            ) {
                val receive = response.body() as List<BoardResponseDto>
                for(b: BoardResponseDto in receive) {
                    if(!b.image.isNullOrEmpty()) {
                        retrofitService.service.getBoardImage(b.boardId).enqueue(object :
                            Callback<ResponseBody> {
                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                val receive = response.body()?.byteStream()
                                coroutineScope.launch {
                                    val deferredimage = coroutineScope.async(Dispatchers.IO) {
                                        BitmapFactory.decodeStream(receive)
                                    }
                                    val image = deferredimage.await()
                                    items.add(AdvertiseModel(b.nickname,b.contents,b.date,image,b.likeCount, b.chatCount))
                                    rvAdapter.notifyDataSetChanged()
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Toast.makeText(activity,"받아오기 실패", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }else {
                        retrofitService.service.getBoardImage(b.boardId).enqueue(object :
                            Callback<ResponseBody> {
                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                val receive = response.body()?.byteStream()
                                val image = BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.image
                                )
                                items.add(AdvertiseModel(b.nickname,b.contents,b.date,image,b.likeCount, b.chatCount))
                                rvAdapter.notifyDataSetChanged()
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Toast.makeText(activity,"받아오기 실패", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
            }

            override fun onFailure(call: Call<List<BoardResponseDto>>, t: Throwable) {

            }
        })



        binding.write.setOnClickListener {
            startActivity(Intent(requireContext(), WriteActivity::class.java).putExtra("타입","ADVERTISE_BOARD"))
        }


    }
}