package com.yndoo.goodwords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class SentenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence)

        val sentenceList = mutableListOf<String>()
        sentenceList.add("시간이 흐른다고 미래가 되지는 않는다.")
        sentenceList.add("먼 과거에 몰두하지 말고 가까운 현재를 파악하라.")
        sentenceList.add("인생에서 가장 진귀한 것은 시간이다.")
        sentenceList.add("결혼에는 많은 고통이 있지만 독신에는 아무런 즐거움이 없다.")
        sentenceList.add("야구에 만약이란 없습니다. 만약이란 걸 붙이면 다 우승하죠.")
        sentenceList.add("지식은 사랑이자, 빛이자, 통찰력이다.")

        val myadapter = ListViewAdapter(sentenceList)
        val listview = findViewById<ListView>(R.id.sentenceListView)

        listview.adapter = myadapter
    }
}