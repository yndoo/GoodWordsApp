package com.yndoo.goodwords

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.yndoo.goodwords.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sentenceList = mutableListOf<String>()
        sentenceList.add("시간이 흐른다고 미래가 되지는 않는다.")
        sentenceList.add("먼 과거에 몰두하지 말고 가까운 현재를 파악하라.")
        sentenceList.add("인생에서 가장 진귀한 것은 시간이다.")
        sentenceList.add("결혼에는 많은 고통이 있지만 독신에는 아무런 즐거움이 없다.")
        sentenceList.add("야구에 만약이란 없습니다. 만약이란 걸 붙이면 다 우승하죠.")
        sentenceList.add("지식은 사랑이자, 빛이자, 통찰력이다.")

        if (intent.getStringExtra("No?")=="No"){
            //No메시지가 오면 데이터요청임!
            //list를 보내줌
            val intent3 = Intent()
            intent3.putStringArrayListExtra("list",ArrayList(sentenceList))
            setResult(Activity.RESULT_OK, intent3)

            finish()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.showAllSentenceBtn.setOnClickListener {
            // 전체 리스트 보기 버튼 눌리면 SentenceActivity로감

            val intent = Intent(this, SentenceActivity::class.java)
            startActivity(intent)
        }

        binding.goodwordTextView.text = sentenceList.random()


    }
}