package com.android.youtubeproject.fragment.myvideofragment

import CustomDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.databinding.FragmentMyVideoBinding
import com.android.youtubeproject.fragment.myvideofragment.db.MyDatabase
import com.android.youtubeproject.fragment.myvideofragment.db.UserData
import com.android.youtubeproject.fragment.myvideofragment.repository.MyVideoRepository
import com.android.youtubeproject.fragment.myvideofragment.viewmodel.MyVideoViewModel
import com.android.youtubeproject.fragment.myvideofragment.viewmodel.MyVideoViewModelFactory
import com.android.youtubeproject.fragment.videodetailfragment.VideoDetail
import com.google.gson.GsonBuilder


class MyVideoFragment : Fragment() {
    private lateinit var mContext: Context

    private lateinit var binding: FragmentMyVideoBinding
    private lateinit var adapter: MyVideoFragmentAdapter

    private val viewModel: MyVideoViewModel by viewModels {
        MyVideoViewModelFactory(MyVideoRepository(MyDatabase.getDatabase().getUser()))
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = MyVideoFragmentAdapter(mContext)

        binding = FragmentMyVideoBinding.inflate(inflater, container, false).apply {
            rvMyvideo.layoutManager = GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false)
            rvMyvideo.adapter = adapter
        }


        adapter.dataChangeListener = object : MyVideoFragmentAdapter.DataChangeListener {
            override fun onDataChanged(isEmpty: Boolean) {
                // 데이터가 로드되었을 때 (또는 데이터 상태 변경 시):
                val recyclerView = binding.rvMyvideo
                val defaultImage = binding.btNewplaylist
                if (isEmpty) {
                    defaultImage.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    defaultImage.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }

        binding.ivDialog.setOnClickListener {
            CustomDialog(onSave = { item: UserData ->
                viewModel.insertUser(item)
                requestUpdateUser(item.id)
            }).show(requireFragmentManager(), "")
        }

        binding.btNewplaylist.setOnClickListener {

        }

        //리스트아이템 클릭 시, 상세화면 표시
        adapter.itemClick = object : MyVideoFragmentAdapter.ItemClick{
            override fun onClick(position: Int, item: YoutubeModel) {
                val intent = Intent(requireContext(), VideoDetail::class.java)
                val gson = GsonBuilder().create()
                val data = gson.toJson(item)
                intent.putExtra("itemdata", data)
                startActivity(intent)
            }
        }

        // Fragment가 생성될 때 한 번만 관찰자를 등록
        observeUserUpdates()
//        requestUpdateUser(SharedPref.getString(requireContext(), "userId", ""))

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.addItems(MyPageFunc.loadVideos(), true)
    }

//    private fun saveIdInSpf(id: String) {
//        SharedPref.setString(requireContext(), "userId", id)
//    }

    // 사용자 정보를 요청하는 함수
    private fun requestUpdateUser(id: String) {
        viewModel.getUserById(id)
    }

    // 사용자 정보가 변경될 때 UI를 업데이트하는 함수
    private fun observeUserUpdates() {
        viewModel.selectedUser.observe(viewLifecycleOwner) { userData ->
            userData?.let {
                binding.ivUser.setImageURI(it.picture)
                binding.tvNickname.text = it.nickname
                binding.tvUserId.text = it.id
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}