# 명언 애플리케이션
ListView를 사용한 랜덤 명언 애플리케이션을 개발하였다. 

## 완성 모습
애플리케이션 실행 시 랜덤 명언이 보이고, '전체 명언 보기' 버튼 클릭 시 모든 명언의 리스트가 보이는 모습이다.
![](https://velog.velcdn.com/images/kuronuma_daisy/post/4d3276fd-c15a-480a-843b-eb1630121530/image.gif)


## Ver.1때 소감
#### MainActivity.kt
코드를 잘 보면 MainActivity와 SentenceActivity에서 같은 명언 리스트를 각각 새로 선언하여 사용한다.   

`sentenceList`라는 리스트를 하드코딩하였다. 그런데 이걸 똑같이 SentenceActivity에서도 하드코딩하여 사용했다! 
MainActivity에서 선언한 sentenceList를 활용해서 한 번만 선언하면 되는 구조로 바꾸고 싶어 intent.putExtra 종류를 활용해서 시도해봤는데, 잘 안 된다. 😭
아직 모르는 개념이 더 있을 것 같다... 차근차근 알아가야지.!

# Ver.2
저번에 만든 명언 앱에서 두 액티비티에 같은 List가 각각 하드코딩되어있는 점을 아쉬워했었다.  
`setResult`와 `onActivityResult`등을 알게되어 이를 사용해서 보완해보았다.
> startActivityForResult()를 통해 액티비티를 생성하면 액티비티가 종료될 때 지정한 requestCode와 함께 onActivityResult()메소드를 호출한다. 이때 종료한 액티비티를 호출한 액티비티로 결과를 반환하기 위해서 setResult()를 호출하여 resultCode와 result Intent를 지정할 수 있다.
[책 읽는 개발자_테드](https://scshim.tistory.com/50)
>

# 데이터 흐름
우선 `sentenceList`라는 보내야 할 List데이터가 있다. 이 List는 MainActivity.kt에 선언 및 초기화되어 있으며, SentenceActivity.kt에서도 이 List가 필요하다.  

보여지기엔 SentenceActivity가 MainActivity에 데이터를 요청해서 가져오는 것으로 보인다.  
이 `sentenceList`가 주고 받아지는 흐름은 다음과 같다.

1. MainActivity에서 '전체 리스트 보기' 버튼을 클릭 시 SentenceActivity로 이동.
2. SentenceActivity에서는 List가 필요하므로 **startActivityForResult()**를 통해 MainActivity를 생성.
3. MainActivity는 (다른 경로가 아닌)SentenceActivity에서 들렀을 경우 List를 intent에 포함시켜 **setResult()**를 호출.
4. MainActivity가 종료되며 반환된 데이터를 **onActivityResult()**를 통해 사용.

# 코드
### MainActivity.kt
```kotlin
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
```
### SentenceActivity.kt
```kotlin
package com.yndoo.goodwords

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView

class SentenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence)

        // 데이터 요청?
        val intent2 = Intent(this, MainActivity::class.java)
        intent2.putExtra("No?","No")
        startActivityForResult(intent2, 222)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != RESULT_OK){
            return
        }
        if(requestCode == 222){
            //MainActivity에서 intent3으로 보내준 list 받아옴
            val res = data!!.getStringArrayListExtra("list")
            Log.d("*****", res.toString())

            val myadapter = ListViewAdapter(res!!.toMutableList())
            val lv = findViewById<ListView>(R.id.sentenceListView)

            lv.adapter = myadapter
        }
    }
}
```

# 느낀 점(a.k.a. 또 아쉬운 점)
인프런 강의를 듣다가 해보고 싶은 점이 생겼던 것이고, 해결하지 못해 결국 강의 게시판에 질문을 남겼었다. 며칠이 지나고 '인프런 AI 인턴' 이라는 게시자에게 setResult를 활용하여 해결해볼 수 있겠다는 답변을 받았고 열심히 해봤다. 그리고 내가 원하는 모양새가 완성되어 기뻐하다가, 답변이 하나 더 달려서 보니 인프런 강의 선생님(?)인 '개복치개발자'님께서 아래와 같은 답변을 주셨다.  
![](https://velog.velcdn.com/images/kuronuma_daisy/post/9f864bcd-960d-4f80-a79d-9836253ff9ea/image.png)  

> 전체 리스트를 전달하는 것은 일반적이지 않다..... 일반적으로 ViewModel을 사용한다!  

갑자기 이상한 것에 꽂혀 불필요한 곳에 매달린 것 같아 부끄러웠다..😭 
그래도 몰랐던 setResult을 알게 되었으니 좋은 경험이겠지, 언젠가 살이 되겠지!
선생님께서 알려주신 ViewModel을 활용하는 방법은 내가 최근 안드로이드 공부를 다시 하게 된 이후로 계속 들려오는 키워드이다. 얼른 초보단계를 벗어나 중급으로 향해가야지, ViewModel과 그를 활용한 LiveData를 꼭 쓸줄알게되어야지!

# [벨로그에서 보기](https://velog.io/@kuronuma_daisy/Android-%EC%95%A1%ED%8B%B0%EB%B9%84%ED%8B%B0%EA%B0%84-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%A0%84%EC%86%A1-setResult-%ED%99%9C%EC%9A%A9-%EB%9E%9C%EB%8D%A4-%EB%AA%85%EC%96%B8-%EC%95%B1-ver.2)
