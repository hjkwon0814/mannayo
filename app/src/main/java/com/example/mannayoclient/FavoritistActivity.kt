package com.example.mannayoclient

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.categorylist.CategoryModel
import com.example.mannayoclient.categorylist.CategoryRVAdapter
import com.example.mannayoclient.categorylist.restaurantInfo
import com.example.mannayoclient.databinding.ActivityFavoritistBinding
import com.example.mannayoclient.mainmenulist.MainStoreActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritistActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoritistBinding
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityFavoritistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = ArrayList<CategoryModel>()

        val shared = this.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val edit = shared.edit()
        edit.putString("Jjim", "false")
        val memberId = shared.getString("id",null)?.toLong()

        val nextIntent = Intent(this, MainStoreActivity::class.java)

        val rv: RecyclerView = binding.fRv
        val rvAdapter = CategoryRVAdapter(items)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)
        rvAdapter.itemClick = object : CategoryRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                edit.putString("restaurantId", items[position].restaurantId.toString())
                edit.putString("Jjim", items[position].Check.toString())
                edit.commit()
                startActivity(nextIntent)
            }

            override fun onHeartClick(view: View, position: Int) {
                edit.putString("restaurantId", items[position].restaurantId.toString())
                edit.commit()
                if(items[position].Check) {// 체크 박스 확인 true
                    items[position].Check = false
                    println("heartclick2 =" + items[position].Check)
                    retrofitService.service.deleteJjim(shared.getString("id",null)?.toLong(),shared.getString("restaurantId", null)?.toLong()).enqueue(object : Callback<ReceiveOK> {
                        override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                            val receive = response.body() as ReceiveOK
                            if(response.isSuccessful && receive.success) {
                                Toast.makeText(this@FavoritistActivity,"찜 등록 해제 되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                        }

                    })
                }
                else if(!items[position].Check) { //false
                    items[position].Check = true
                    println("heartclick3 =" + items[position].Check)
                    retrofitService.service.setJjim(shared.getString("id",null)?.toLong(),shared.getString("restaurantId", null)?.toLong()).enqueue(object : Callback<ReceiveOK> {
                        override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                            val receive = response.body() as ReceiveOK
                            if(response.isSuccessful && receive.success) {
                                Toast.makeText(this@FavoritistActivity,"찜 등록 되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                        }
                    })

                }
            }


        }

        retrofitService.service.getRestaurantJjimList(memberId)
            .enqueue(object : Callback<List<restaurantInfo>> {
                override fun onResponse(
                    call: Call<List<restaurantInfo>>,
                    response: Response<List<restaurantInfo>>
                ) {
                    val receive = response.body() as List<restaurantInfo>
                    if (response.isSuccessful) {
                        for (it in receive) {
                            if (!it.imageAddress.equals("")) {
                                retrofitService.service.getRestaurantImage(it.id)
                                    .enqueue(object : Callback<ResponseBody> {
                                        override fun onResponse(
                                            call: Call<ResponseBody>,
                                            response: Response<ResponseBody>
                                        ) {
                                            val receiveimage = response.body()?.byteStream()
                                            if (response.isSuccessful) {
                                                coroutineScope.launch {
                                                    val originalDeferred =
                                                        coroutineScope.async(Dispatchers.IO) {
                                                            BitmapFactory.decodeStream(receiveimage)
                                                        }
                                                    val originalBitmap = originalDeferred.await()
                                                    items.add(
                                                        CategoryModel(
                                                            it.name,
                                                            it.address,
                                                            it.starttime + "~" + it.endtime,
                                                            it.point.toString(),
                                                            originalBitmap,
                                                            it.id,
                                                            it.isJjim,
                                                            it.countReview,
                                                            it.countJjim
                                                        )
                                                    )
                                                    println("restaurantIsJJim =" + it.isJjim)
                                                    rvAdapter.notifyDataSetChanged()
                                                }
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<ResponseBody>,
                                            t: Throwable
                                        ) {
                                            Log.e("imageTest", "${t.message}")
                                        }

                                    })
                            } else {
                                val bitmap = BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.component_101
                                )
                                items.add(
                                    CategoryModel(
                                        it.name,
                                        it.address,
                                        it.starttime + "~" + it.endtime,
                                        it.point.toString(),
                                        bitmap,
                                        it.id,
                                        it.isJjim,
                                        it.countReview,
                                        it.countJjim
                                    )
                                )
                                rv.adapter = rvAdapter
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<List<restaurantInfo>>, t: Throwable) {
                    Log.e("test", "${t.message}")
                }


            })

    }


}