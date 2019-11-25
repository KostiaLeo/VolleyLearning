package com.example.volleylearninng

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.example.volleylearninng.pojo.MyNewz
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.DisposableSubscriber
import org.json.JSONObject
import java.io.File

class MainActivity : AppCompatActivity() {
    private val TAG = "MyTAG"
    private val URL = "https://api.myjson.com/bins/1cwqty"
    private var myNewz: MyNewz? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        simpleVolleyRequest()

    }

    private fun simpleVolleyRequest() {
        RequestHandler.getInstance(this).addToJsonRequestQueue(request)
    }

    private val request: JsonObjectRequest by lazy {
        JsonObjectRequest(Request.Method.GET, URL, null,
            Response.Listener {
                val myNewz: MyNewz = Gson().fromJson(it.toString(), MyNewz::class.java)
                myNewz.results.forEach { artists ->
                    println(artists.name)
                }
            },
            Response.ErrorListener {
                println(it)
            }
        ).apply {
            tag = TAG
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RequestHandler.getInstance(this).queue.cancelAll(TAG)
        println("I've stopped queue")
    }
}