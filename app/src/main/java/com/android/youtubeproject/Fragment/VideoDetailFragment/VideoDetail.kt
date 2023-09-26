package com.android.youtubeproject.Fragment.VideoDetailFragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.android.youtubeproject.R
import com.android.youtubeproject.databinding.ActivityVideoDetailBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideoDetail : AppCompatActivity() {
    private val binding by lazy { ActivityVideoDetailBinding.inflate(layoutInflater) }
    var btmstate = false
    var bt2state = false
    var bt3state = false
    var bt4state = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.videoDetailFbtmain.setOnClickListener {
            ButtonAction("main")
        }
        binding.videoDetailFbt1.setOnClickListener {
            ButtonAction("infomation")
        }
        binding.videoDetailFbt2.setOnClickListener {
            ButtonAction("share")
        }
        binding.videoDetailFbt3.setOnClickListener {
            ButtonAction("dislike")
        }
        binding.videoDetailFbt4.setOnClickListener {
            ButtonAction("like")
        }
    }

    fun ButtonAction(buttontype: String) {
        when (buttontype) {
            "main" -> {
                /*
                delay는 코루틴 스코프 내에서만 호출될 수 있는 함수이기 때문에 코루틴 스코프에서 호출해야 한다.
                lifecycleScope.launch를 사용하여 메인 스레드에서 코루틴을 실행할수 있다.
                한 편, 메인 스레드에서 딜레이를 주는게 안전한지 나중에 물어보기
                딜레이 타임을 한번에 조절하기 위해 Long타입 변수로 주었고,
                com.google.android.material.floatingactionbutton.FloatingActionButton 위젯은 hide()와 show()라는 메서드를 사용하여
                애니메이션을 쉽게 줄 수 있다.
                */
                lifecycleScope.launch {
                    val delaytime = 50L
                    if (!btmstate) {
                        binding.videoDetailFbtmain.hide()
                        binding.videoDetailFbt1.show()
                        delay(delaytime)
                        binding.videoDetailFbt2.show()
                        delay(delaytime)
                        binding.videoDetailFbt3.show()
                        delay(delaytime)
                        binding.videoDetailFbt4.show()
                        delay(delaytime)
                        binding.videoDetailFbtmain.show()
                    } else {
                        binding.videoDetailFbtmain.hide()
                        binding.videoDetailFbt1.hide()
                        delay(delaytime)
                        binding.videoDetailFbt2.hide()
                        delay(delaytime)
                        binding.videoDetailFbt3.hide()
                        delay(delaytime)
                        binding.videoDetailFbt4.hide()
                        delay(delaytime)
                        binding.videoDetailFbtmain.show()
                    }
                    btmstate = !btmstate
                }
            }

            "infomation" -> {

            }

            "share" -> {
                bt2state = !bt2state
                if (!bt2state) {
                    binding.videoDetailFbt2.setImageResource(R.drawable.ic_video_detail_share_false)
                } else if (bt2state) {
                    binding.videoDetailFbt2.setImageResource(R.drawable.ic_video_detail_share_true)
                }

            }

            "dislike" -> {
                bt3state = !bt3state
                if (!bt3state) {
                    binding.videoDetailFbt3.setImageResource(R.drawable.ic_video_detail_dislike_false)
                } else if (bt3state) {
                    binding.videoDetailFbt3.setImageResource(R.drawable.ic_video_detail_dislike_true)
                }

            }

            "like" -> {
                bt4state = !bt4state
                if (!bt4state) {
                    binding.videoDetailFbt4.setImageResource(R.drawable.ic_video_detail_like_false)
                } else if (bt4state) {
                    binding.videoDetailFbt4.setImageResource(R.drawable.ic_video_detail_like_true)
                }

            }
        }
    }
}