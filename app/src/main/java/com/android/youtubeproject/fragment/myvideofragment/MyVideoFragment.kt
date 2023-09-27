package com.android.youtubeproject.fragment.myvideofragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.youtubeproject.fragment.myvideofragment.MyPageFunc
import com.android.youtubeproject.fragment.myvideofragment.MyVideoFragmentAdapter
import com.android.youtubeproject.databinding.FragmentMyVideoBinding


class MyVideoFragment : Fragment() {
    private lateinit var mContext: Context


    private var binding: FragmentMyVideoBinding? = null
    private lateinit var adapter: MyVideoFragmentAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = MyVideoFragmentAdapter(mContext)

        binding = FragmentMyVideoBinding.inflate(inflater, container, false).apply {
            rvMyvideo.layoutManager = GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false)
            rvMyvideo.adapter = adapter
        }

        adapter.addItems(MyPageFunc.loadVideos(requireContext()), true)

        return binding?.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // 메모리 누수를 방지하기 위해 뷰가 파괴될 때 바인딩 객체를 null로 설정
        binding = null
    }

}