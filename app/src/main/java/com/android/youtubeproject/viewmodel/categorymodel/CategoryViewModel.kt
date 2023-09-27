package com.android.youtubeproject.viewmodel.categorymodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.serverdata.CategoryData
import com.android.youtubeproject.api.NetWorkInterface
import com.android.youtubeproject.api.model.CategoryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(private val apiService:NetWorkInterface): ViewModel() {

    private val _categoryResults = MutableLiveData<List<CategoryModel>>()
    val categoryResults : LiveData<List<CategoryModel>> get() = _categoryResults

    var categoryItems : ArrayList<CategoryModel> = ArrayList()

     fun categoryServerResults(){
        apiService.getCategory("snippet","KR")?.enqueue(object : Callback<CategoryData?>{
            override fun onResponse(call: Call<CategoryData?>, response: Response<CategoryData?>) {
                response.body()?.let {
                    for(category in response.body()!!.items){
                        val title = category.snippet.title
                        categoryItems.add(CategoryModel(Constants.NATION_TYPE,title))
                    }
                }
                categoryDataResults()
            }

            override fun onFailure(call: Call<CategoryData?>, t: Throwable) {
                Log.e("YouTubeProjects", "에러 : ${t.message}")
            }

        })
    }
    private fun categoryDataResults(){
        _categoryResults.value = categoryItems
        Log.d("YouTubeProjects", " _categoryResults.value데이터 : ${ _categoryResults.value}")
    }
}