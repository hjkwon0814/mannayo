package com.example.mannayoclient.todaylist.today_detail

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.ReplyDialogFragment
import com.example.mannayoclient.SendChatActivity
import com.example.mannayoclient.advertiselist.AdvertiseActivity
import com.example.mannayoclient.categorylist.CategoryRVAdapter
import com.example.mannayoclient.databinding.TodaydetailFragBinding
import com.example.mannayoclient.dto.BoardResponseDto
import com.example.mannayoclient.dto.ReceiveOK
import com.example.mannayoclient.dto.VoteResponseDto
import com.example.mannayoclient.dto.commentDto
import com.example.mannayoclient.mainmenulist.MainStoreActivity
import com.example.mannayoclient.retrofitService
import com.example.mannayoclient.todaylist.TodayActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodayDetailFragment : Fragment(R.layout.todaydetail_frag) {
    lateinit var binding: TodaydetailFragBinding
    lateinit var activity: TodayActivity
    val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = TodaydetailFragBinding.bind(view)

        activity = context as TodayActivity
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


        val rv: RecyclerView = binding.dVoteRecyclerView
        val items = ArrayList<TodayVoteModel>()
        val rvAdapter = TodayVoteRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())

        // set item
        rvAdapter.setItem(ArrayList())

        // click listener
        rvAdapter.setItemClickListener(object :TodayVoteRVAdapter.ItemClick {
            override fun onCheckBoxClick(view: View, todayVoteModel: TodayVoteModel) {
                TODO("Not yet implemented")
            }
        })

        //투표하기 버튼
        binding.voteGo.setOnClickListener{
            binding.tVoteGoText.text = "투표 완료"
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
                    binding.replyEdit.hint = "댓글을 입력하세요."
                }else {
                    if(list[position].count >= 1) {
                        println(2)
                        commentid = shared.getString("commentid", null)?.toLong()
                        binding.replyEdit.hint = "대댓글을 입력하세요."
                    }else {
                        println(3)
                        list[position].isClicked = true
                        editor.putString("depth", "2")
                        editor.putString("commentid", list[position].id.toString())
                        editor.commit()
                        commentid = shared.getString("commentid", null)?.toLong()
                        binding.replyEdit.hint = "대댓글을 입력하세요."
                    }
                }
            }

            override fun onNickClick(view: View, position: Int) {
                val bottomSheet = ReplyDialogFragment()
                bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            }

            override fun onDeleteClick(view: View, position: Int) {
                TODO("Not yet implemented")
            }

        }

        if(!(boardid==memberid)) {
            binding.correction.visibility = View.GONE
            binding.delete.visibility = View.GONE
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
                    retrofitService.service.getVote(receive.boardId,memberid).enqueue(object :
                        Callback<List<VoteResponseDto>> {
                        override fun onResponse(
                            call: Call<List<VoteResponseDto>>,
                            response: Response<List<VoteResponseDto>>
                        ) {
                            val receive = response.body() as List<VoteResponseDto>
                            for(v : VoteResponseDto in receive) {
                                items.add(TodayVoteModel(v.contents, v.count, v.amIVote))
                                rvAdapter.notifyDataSetChanged()
                            }
                        }

                        override fun onFailure(call: Call<List<VoteResponseDto>>, t: Throwable) {
                            TODO("Not yet implemented")
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
                            Toast.makeText(activity,"받아오기 실패", Toast.LENGTH_SHORT).show()
                        }
                    })

                }else {
                    binding.imageview.visibility = View.GONE
                }

                binding.textView63.text = receive.nickname
                binding.textView64.text = receive.date
                binding.textView65.text = receive.contents
                if(receive.isProfile) {
                    retrofitService.service.getMyProfileImage(memberid).enqueue(object :
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
                            list.add(TodayReplyModel(TodayReplyModel.reply1,c.nickname, c.date, c.contents, c.id,false,0L, c.writerid, memberid))
                            binding.textView71.text = "댓글(" + comment.toString() + ")"
                            adpater.notifyDataSetChanged()
                        }else {
                            list.add(TodayReplyModel(TodayReplyModel.reply2,c.nickname, c.date,c.contents, c.id,false,0L, c.writerid, memberid))
                            binding.textView71.text = "댓글(" + comment.toString() + ")"
                            adpater.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<List<commentDto>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }

        binding.imageView86.setOnClickListener {
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

        /*투포있으면 투표창 보이게 없으면 안보이게
        binding.voteConstraintLayout.visibility = View.VISIBLE //보이게
        binding.voteConstraintLayout.visibility = View.GONE    //안보이게 */


        /*이미지있으면 이미지창 보이게 없으면 안보이게
        binding.imageview.visibility = View.VISIBLE //보이게
        binding.imageview.visibility = View.GONE   //안보이게 */

        /*작성자일 떄 수정, 삭제 버튼 보이고 신고 버튼 안보이게
          아니면 수정, 삭제 버튼은 안보이고 신고 버튼은 보이게
        binding.correction.visibility = View.VISIBLE
        binding.delete.visibility = View.VISIBLE
        binding.singo.visibility = View.GONE //작성자
        binding.correction.visibility = View.GONE
        binding.delete.visibility = View.GONE
        binding.singo.visibility = View.VISIBLE //작성자xxxx */



        ///////체크박스 하나만 클릭 가능하게 + 튜표하기 클릭이벤트처리 미완성


    }
}