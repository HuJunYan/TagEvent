package com.hjy.tagevent.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hjy.tagevent.R
import com.hjy.tagevent.TagEventBus
import com.hjy.tagevent.event.Tag
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
          TagEventBus.getDefault().register(this)
        bt.setOnClickListener {
            TagEventBus.getDefault().post(Tag.PAYSUCESSFUL)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TagEventBus.getDefault().unregister(this)
    }
}
