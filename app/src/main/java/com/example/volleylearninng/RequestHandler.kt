package com.example.volleylearninng

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.collection.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.example.volleylearninng.pojo.MyNewz
import com.google.gson.Gson
import java.io.File

class RequestHandler constructor(val context: Context){
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: RequestHandler? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(Any()) {
                INSTANCE ?: RequestHandler(context).also {
                    INSTANCE = it
                }
            }
    }

    fun <T> addToJsonRequestQueue(r: Request<T>){
        queue.add(r)
    }

    val queue: RequestQueue by lazy {
        getRequestQueue()
    }

    private fun getRequestQueue(): RequestQueue {
        val myCacheDir = File(context.cacheDir, "http")
        val cache = DiskBasedCache(myCacheDir, 1024 * 1024)
        val network = BasicNetwork(HurlStack())

        return RequestQueue(cache, network).apply {
            start()
        }

        /* alternative RequestQueue creating:
        *     return Volley.newRequestQueue(context)
        */
    }

//    val imageLoader: ImageLoader by lazy {
//        ImageLoader(queue,
//            object : ImageLoader.ImageCache {
//                private val cache = LruCache<String, Bitmap>(20)
//                override fun getBitmap(url: String): Bitmap? {
//                    return cache.get(url)
//                }
//                override fun putBitmap(url: String, bitmap: Bitmap) {
//                    cache.put(url, bitmap)
//                }
//            })
//    }
}
