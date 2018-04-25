package com.hjy.tagevent.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.hjy.tagevent.R
import com.hjy.tagevent.Subscribe
import com.hjy.tagevent.TagEventBus
import com.hjy.tagevent.event.Tag

class MainActivity : AppCompatActivity() {

    public var i:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TagEventBus.getDefault().register(this)

        TagEventBus.getDefault().post(Tag.LOGINSUCESSFUL)
        i++
    }


    @Subscribe(Tag.LOGINSUCESSFUL)
    fun testTag () {
        Log.i("abc","收到消息"+i)
    }


    @Subscribe(Tag.PAYSUCESSFUL)
    fun testFinish(){
        finish()
        Log.i("abc","关闭界面")
    }

     fun got(view:View) {

         val intent = Intent(this, PostActivity::class.java)
         startActivity(intent)

    }

    override fun onDestroy() {
        super.onDestroy()

        TagEventBus.getDefault().unregister(this)
    }
}
