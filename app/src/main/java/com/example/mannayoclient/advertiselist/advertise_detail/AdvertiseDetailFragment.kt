package com.example.mannayoclient.advertiselist.advertise_detail

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.ReplyDialogFragment
import com.example.mannayoclient.advertiselist.AdvertiseActivity
import com.example.mannayoclient.advertiselist.AdvertiseModel
import com.example.mannayoclient.databinding.AdvertisedetailFragBinding
import com.example.mannayoclient.dto.BoardResponseDto
import com.example.mannayoclient.dto.ReceiveOK
import com.example.mannayoclient.dto.VoteResponseDto
import com.example.mannayoclient.dto.commentDto
import com.example.mannayoclient.retrofitService
import com.example.mannayoclient.todaylist.today_detail.TodayReplyModel
import com.example.mannayoclient.todaylist.today_detail.TodayReplyRVAdapter
import com.example.mannayoclient.todaylist.today_detail.TodayVoteModel
import com.example.mannayoclient.todaylist.today_detail.TodayVoteRVAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdvertiseDetailFragment : Fragment(R.layout.advertisedetail_frag) {
    lateinit var binding: AdvertisedetailFragBinding
    lateinit var activity: AdvertiseActivity
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = AdvertisedetailFragBinding
            .bind(view)
        activity = context as AdvertiseActivity
        val shared = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.putString("depth","1")
        editor.commit()
        var depth = shared.getString("depth", null)?.toLong()
        val boardid = shared.getString("boardid", null)?.toLong()
        val writeid = shared.getString("writerid",null)?.toLong()
        val memberid = shared.getString("id", null)?.toLong()
        val comment = shared.getString("commentCount", null)?.toLong()
        var commentid : Long? = 0
        var voteid : Long? = shared.getString("voteid","0")?.toLong()

        val rv: RecyclerView = binding.dVoteRecyclerView
        val items = ArrayList<TodayVoteModel>()
        val rvAdapter = TodayVoteRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())

        // set item
        rvAdapter.setItem(ArrayList())

        // click listener
        rvAdapter.setItemClickListener(object :TodayVoteRVAdapter.ItemClick {
            override fun onCheckBoxClick(view: View, position: Int) {
                editor.putString("voteid", items[position].id.toString())
                editor.commit()
            }

        })

        //???????????? ??????
        binding.voteGo.setOnClickListener{
            voteid = shared.getString("voteid",null)?.toLong()
            if(!voteid?.equals(0L)!!) {
                retrofitService.service.setToVote(memberid,voteid).enqueue(object : Callback<ReceiveOK> {
                    override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                        if(response.isSuccessful) {
                            Toast.makeText(activity, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {
                        Toast.makeText(activity, "???????????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
                    }
                })
                binding.voteGoText.text = "?????? ??????"
                binding.voteGo.isClickable = false
            }else {
                Toast.makeText(activity, "????????? ???????????????!", Toast.LENGTH_SHORT).show()
            }
        }



        //Reply
        val rv2: RecyclerView = binding.replyRecyclerView

        val list = ArrayList<TodayReplyModel>()


        val adpater = TodayReplyRVAdapter(list)
        rv2.layoutManager = LinearLayoutManager(requireContext())
        rv2.adapter = adpater

        adpater.itemClick = object : TodayReplyRVAdapter.ItemClick {
            override fun onChatClick(view: View, position: Int) {

                if(list[position].isClicked) {
                    println(1)
                    list[position].isClicked = false
                    editor.putString("depth", "1")
                    editor.putString("commentid", list[position].id.toString())
                    editor.commit()
                    commentid = shared.getString("commentid",null)?.toLong()
                    binding.replyEdit.hint = "????????? ???????????????."
                }else {
                    if(list[position].count >= 1) {
                        println(2)
                        commentid = shared.getString("commentid", null)?.toLong()
                        binding.replyEdit.hint = "???????????? ???????????????."
                    }else {
                        println(3)
                        list[position].isClicked = true
                        editor.putString("depth", "2")
                        editor.putString("commentid", list[position].id.toString())
                        editor.commit()
                        commentid = shared.getString("commentid", null)?.toLong()
                        binding.replyEdit.hint = "???????????? ???????????????."
                    }
                }
            }

            override fun onNickClick(view: View, position: Int) {
                val bottomSheet = ReplyDialogFragment()
                bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            }

            override fun onDeleteClick(view: View, position: Int) {

            }
        }

        if(!(boardid==memberid)) {
            binding.correction.visibility = View.GONE
            binding.delete2.visibility = View.GONE
        }

        binding.imageView88.setOnClickListener {
            retrofitService.service.setLike(memberid,boardid).enqueue(object : Callback<ReceiveOK> {
                override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                    val receive = response.body() as ReceiveOK
                    if(response.isSuccessful) {
                        Toast.makeText(activity, receive.response, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                }
            })
        }

        retrofitService.service.getBoard(boardid).enqueue(object : Callback<BoardResponseDto> {
            override fun onResponse(
                call: Call<BoardResponseDto>,
                response: Response<BoardResponseDto>
            ) {
                val receive = response.body() as BoardResponseDto
                if(receive.isVote) {
                    binding.voteConstraintLayout.visibility = View.VISIBLE
                    retrofitService.service.getVote(receive.boardId,memberid).enqueue(object : Callback<List<VoteResponseDto>> {
                        override fun onResponse(
                            call: Call<List<VoteResponseDto>>,
                            response: Response<List<VoteResponseDto>>
                        ) {
                            val receive = response.body() as List<VoteResponseDto>
                            for(v : VoteResponseDto in receive) {
                                println(v.contents)
                                println(v.count)
                                println(v.amIVote)
                                items.add(TodayVoteModel(v.contents, v.count, v.amIVote,v.id))
                                if(v.amIVote) {
                                    binding.voteGoText.text = "?????? ??????"
                                    binding.voteGo.isClickable = false
                                }
                            }
                            rvAdapter.notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<List<VoteResponseDto>>, t: Throwable) {

                        }
                    })
                } else {
                    binding.voteConstraintLayout.visibility = View.GONE
                }

                if(!receive.image.isNullOrEmpty()) {
                    binding.imageview.visibility = View.VISIBLE
                    retrofitService.service.getBoardImage(boardid).enqueue(object :
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
                                binding.imageview.setImageBitmap(image);
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(activity,"???????????? ??????", Toast.LENGTH_SHORT).show()
                        }
                    })

                }else {
                    binding.imageview.visibility = View.GONE
                }

                binding.textView63.text = receive.nickname
                binding.textView64.text = receive.date
                binding.textView65.text = receive.contents
                if(receive.isProfile) {
                    retrofitService.service.getMyProfileImage(memberid).enqueue(object : Callback<ResponseBody> {
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
                                binding.imageView69.setImageBitmap(image);
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }

            override fun onFailure(call: Call<BoardResponseDto>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        if(comment!! > 0) {
            retrofitService.service.getComment(boardid).enqueue(object : Callback<List<commentDto>> {
                override fun onResponse(
                    call: Call<List<commentDto>>,
                    response: Response<List<commentDto>>
                ) {
                    val receive = response.body() as List<commentDto>
                    for(c : commentDto in receive) {
                        if(c.depth == 1) {
                            list.add(TodayReplyModel(TodayReplyModel.reply1,c.nickname, c.date, c.contents, c.id,false,0L,c.writerid, memberid))
                            binding.textView71.text = "??????(" + comment.toString() + ")"
                            adpater.notifyDataSetChanged()
                        }else {
                            list.add(TodayReplyModel(TodayReplyModel.reply2,c.nickname, c.date,c.contents, c.id,false,0L,c.writerid, memberid))
                            binding.textView71.text = "??????(" + comment.toString() + ")"
                            adpater.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<List<commentDto>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }

        binding.SendReply.setOnClickListener {
            depth = shared.getString("depth", null)?.toLong()
            println("depth = " + depth)
            if(depth == 1L) {
                retrofitService.service.setReply1(memberid,
                    boardid,
                    binding.replyEdit.text.toString()).enqueue(object : Callback<ReceiveOK> {
                    override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                        if (response.isSuccessful) {
                            activity.finish()
                            activity.overridePendingTransition(0, 0)
                            val intent = activity.intent
                            startActivity(intent)
                            activity.overridePendingTransition(0, 0)
                        }
                    }

                    override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {
                    }
                })
            }else if(depth == 2L) {
                retrofitService.service.setReply2(memberid,commentid,binding.replyEdit.text.toString()).enqueue(object : Callback<ReceiveOK> {
                    override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                        if(response.isSuccessful) {
                            activity.finish()
                            activity.overridePendingTransition(0, 0)
                            val intent = activity.intent
                            startActivity(intent)
                            activity.overridePendingTransition(0, 0)
                        }
                    }

                    override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }


        /*??????????????? ????????? ????????? ????????? ????????????
        binding.voteConstraintLayout.visibility = View.VISIBLE //?????????
        binding.voteConstraintLayout.visibility = View.GONE    //???????????? */


        /*?????????????????? ???????????? ????????? ????????? ????????????
        binding.imageview.visibility = View.VISIBLE //?????????
        binding.imageview.visibility = View.GONE   //???????????? */

        /*???????????? ??? ??????, ?????? ?????? ????????? ?????? ?????? ????????????
          ????????? ??????, ?????? ????????? ???????????? ?????? ????????? ?????????
        binding.correction.visibility = View.VISIBLE
        binding.delete.visibility = View.VISIBLE
        binding.singo.visibility = View.GONE //?????????
        binding.correction.visibility = View.GONE
        binding.delete.visibility = View.GONE
        binding.singo.visibility = View.VISIBLE //?????????xxxx */



        ///////???????????? ????????? ?????? ???????????? + ???????????? ????????????????????? ?????????


    }
}