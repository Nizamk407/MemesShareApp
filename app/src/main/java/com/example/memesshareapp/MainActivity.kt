package com.example.memesshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    private lateinit var iv_memes: ImageView
    private lateinit var pb_memes: ProgressBar
    var currentImageUrl :String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pb_memes = findViewById(R.id.pb_memes)
        iv_memes = findViewById(R.id.iv_memes)
        getmemes()
    }
    private fun getmemes(){
        // Instantiate the RequestQueue.
        pb_memes.visibility= View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                    pb_memes.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pb_memes.visibility = View.GONE
                        return false
                    }

                }).into(iv_memes)
            },
            Response.ErrorListener {})

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun sharememes(view: android.view.View) {

        try {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Check Out Cool Memes I Got From Reddit $currentImageUrl")
            startActivity(sendIntent)
        } catch (e: Exception) {

        }
    }
    fun nextmemes(view: android.view.View) {
        getmemes()
    }
}