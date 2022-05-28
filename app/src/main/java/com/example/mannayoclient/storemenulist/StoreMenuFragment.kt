package com.example.mannayoclient.storemenulist

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.StoremenuFragBinding
import com.example.mannayoclient.dto.ReceiveOK
import com.example.mannayoclient.dto.menu
import com.example.mannayoclient.mainmenulist.MainMenuModel
import com.example.mannayoclient.mainmenulist.MainStoreActivity
import com.example.mannayoclient.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoreMenuFragment : Fragment(R.layout.storemenu_frag) {
    lateinit var binding: StoremenuFragBinding
    lateinit var activity : MainStoreActivity
    val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = StoremenuFragBinding.bind(view)
        activity = context as MainStoreActivity

        val rv: RecyclerView = binding.recyclerView
        val rv2: RecyclerView = binding.recyclerView2

        val items = ArrayList<StoreMenuModel>()
        val items2 = ArrayList<StoreMenuModel>()


        val rvAdapter = StoreMenuRVAdapter(items)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())


        val rvAdapter2 = StoreMenuRVAdapter(items2)
        rv2.adapter = rvAdapter2
        rv2.layoutManager = LinearLayoutManager(requireContext())

        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val id = shared.getString("restaurantId", null)?.toLong()
        val check = shared.getString("Jjim", null)?.toBoolean()
        if(check!!) {
            binding.checkBox2.isChecked = check
        }

        binding.checkBox2.setOnClickListener {
            Jjim()
        }

        retrofitService.service.getMenu(id).enqueue(object : Callback<List<menu>> {
            override fun onResponse(call: Call<List<menu>>, response: Response<List<menu>>) {
                val receive = response.body() as List<menu>
                for(i in receive) {
                    retrofitService.service.getMenuImage(i.id).enqueue(object :
                        Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            val image = response.body()?.byteStream()
                            if(!i.image.isNullOrEmpty()) {
                                coroutineScope.launch {
                                    val deferimage = coroutineScope.async(Dispatchers.IO) {
                                        BitmapFactory.decodeStream(image)
                                    }
                                    val bitMenuImage = deferimage.await()
                                    if(i.isbest) {
                                        items.add(
                                            StoreMenuModel(
                                                "${i.name}\n\n${i.price}원",
                                                bitMenuImage
                                            )
                                        )
                                    }else {
                                        items2.add(
                                            StoreMenuModel(
                                                "${i.name}\n\n${i.price}원",
                                                bitMenuImage
                                            )
                                        )
                                    }
                                    rvAdapter.notifyDataSetChanged()
                                    rvAdapter2.notifyDataSetChanged()
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        }

                    })
                }
            }

            override fun onFailure(call: Call<List<menu>>, t: Throwable) {

            }

        })



    }
    fun Jjim() {
        activity = context as MainStoreActivity
        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val edit = shared.edit()
        if(shared.getString("Jjim", null)?.toBoolean()!!) {// 체크 박스 확인 true
            edit.putString("Jjim","false")
            edit.commit()
            retrofitService.service.deleteJjim(shared.getString("id",null)?.toLong(),shared.getString("restaurantId", null)?.toLong()).enqueue(object : Callback<ReceiveOK> {
                override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                    val receive = response.body() as ReceiveOK
                    if(response.isSuccessful && receive.success) {
                        Toast.makeText(activity,"찜 등록 해제 되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                }

            })
        }
        else if(shared.getString("Jjim", null)?.toBoolean()!!) { //false
            edit.putString("Jjim","true")
            edit.commit()
            retrofitService.service.setJjim(shared.getString("id",null)?.toLong(),shared.getString("restaurantId", null)?.toLong()).enqueue(object : Callback<ReceiveOK> {
                override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                    val receive = response.body() as ReceiveOK
                    if(response.isSuccessful && receive.success) {
                        Toast.makeText(activity,"찜 등록 되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                }
            })

        }
    }


}