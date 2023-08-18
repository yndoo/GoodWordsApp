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