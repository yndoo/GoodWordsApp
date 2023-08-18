# 명언 애플리케이션
ListView를 사용한 랜덤 명언 애플리케이션을 개발하였다. 

## 완성 모습
애플리케이션 실행 시 랜덤 명언이 보이고, '전체 명언 보기' 버튼 클릭 시 모든 명언의 리스트가 보이는 모습이다.
![](https://velog.velcdn.com/images/kuronuma_daisy/post/4d3276fd-c15a-480a-843b-eb1630121530/image.gif)



#### MainActivity.kt
코드를 잘 보면 MainActivity와 SentenceActivity에서 같은 명언 리스트를 각각 새로 선언하여 사용한다.   

`sentenceList`라는 리스트를 하드코딩하였다. 그런데 이걸 똑같이 SentenceActivity에서도 하드코딩하여 사용했다! 
MainActivity에서 선언한 sentenceList를 활용해서 한 번만 선언하면 되는 구조로 바꾸고 싶어 intent.putExtra 종류를 활용해서 시도해봤는데, 잘 안 된다. 😭
아직 모르는 개념이 더 있을 것 같다... 차근차근 알아가야지.!
```kotlin
package com.yndoo.goodwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.yndoo.goodwords.databinding.ActivityMainBinding

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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.showAllSentenceBtn.setOnClickListener {
            val intent = Intent(this, SentenceActivity::class.java)
            startActivity(intent)
        }

        binding.goodwordTextView.setText(sentenceList.random())
    }
}
```
#### SentenceActivity.kt
```kotlin
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
```
