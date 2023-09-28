package com.android.youtubeproject.fragment.myvideofragment

import CustomDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.youtubeproject.databinding.FragmentMyVideoBinding
import com.android.youtubeproject.fragment.myvideofragment.db.MyDatabase
import com.android.youtubeproject.fragment.myvideofragment.db.UserDao
import com.android.youtubeproject.fragment.myvideofragment.db.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyVideoFragment : Fragment() {
    private lateinit var mContext: Context


    private lateinit var binding: FragmentMyVideoBinding
    private lateinit var adapter: MyVideoFragmentAdapter
    private lateinit var mDao: UserDao

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


        adapter.dataChangeListener = object : MyVideoFragmentAdapter.DataChangeListener {
            override fun onDataChanged(isEmpty: Boolean) {
                // 데이터가 로드되었을 때 (또는 데이터 상태 변경 시):
                val recyclerView = binding.rvMyvideo
                val defaultImage = binding.defaultImage
                if (isEmpty) {
                    defaultImage.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    defaultImage.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }

        adapter.addItems(MyPageFunc.loadVideos(), true)

        binding.ivDialog.setOnClickListener {
            CustomDialog(onSave = { item: UserData ->
                CoroutineScope(Dispatchers.IO).launch {
                    //저장밖에 기능이 없어서 여기서 구현
                    mDao.insert(item)
                }


                //화면 갱신
                updateUser(item.id)

            }).show(requireFragmentManager(), "")

        }

        mDao = MyDatabase.getDatabase().getUser()

        return binding.root
    }

    private fun updateUser(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var data = mDao.getUserById(id)

            requireActivity().runOnUiThread {
                data?.let {
                    binding.ivUser.setImageURI(it.picture)
                    binding.tvNickname.text = it.nickname
                    binding.tvUserId.text = it.id
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}