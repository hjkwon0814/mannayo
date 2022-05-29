package com.example.mannayoclient.todaylist.today_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.TodaydetailFragBinding
import com.example.mannayoclient.writelist.WriteModel
import com.example.mannayoclient.writelist.WriteRVAdapter

class TodayDetailFragment : Fragment(R.layout.todaydetail_frag) {
    lateinit var binding: TodaydetailFragBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = TodaydetailFragBinding.bind(view)

        val rv: RecyclerView = binding.dVoteRecyclerView

        val items = ArrayList<TodayVoteModel>()


        val rvAdapter = TodayVoteRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())



        //Reply
        val rv2: RecyclerView = binding.replyRecyclerView

        val list = ArrayList<TodayReplyModel>()

        //test 용
        list.add(TodayReplyModel(TodayReplyModel.reply1,"a","a","a"))
        list.add(TodayReplyModel(TodayReplyModel.reply2,"a","a","a"))
        list.add(TodayReplyModel(TodayReplyModel.reply1,"a","a","a"))
        list.add(TodayReplyModel(TodayReplyModel.reply2,"a","a","a"))
        list.add(TodayReplyModel(TodayReplyModel.reply2,"a","a","a"))



        val adpater = TodayReplyRVAdapter(list)
        rv2.layoutManager = LinearLayoutManager(requireContext())
        rv2.adapter = adpater


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