package com.mosecode.youtubevideoplayerdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.mosecode.youtubevideoplayerdemo.databinding.ActivityMainBinding
import com.mosecode.youtubevideoplayerdemo.network.YoutubeApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var adapter: VideosAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = VideosAdapter(VideosAdapter.OnClickListener{ item ->
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("YOUTUBE_VIDEO_ID", item.id.videoId)
            startActivity(intent)
        })

        binding.searchButton.setOnClickListener {
            binding.progressBar.isVisible = true
            val searchTerm = binding.edtSearch.text.toString().trim()

            YoutubeApi.apiInstance().search(searchTerm).enqueue(object : Callback<SearchResponse>{
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    Log.d(TAG, "onResponse: ${response.isSuccessful}")
                    binding.progressBar.isVisible = false
                    val result = response.body()?.items
                    adapter.submitList(result)
                    binding.videosRecycler.adapter = adapter
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    binding.progressBar.isVisible = false
                    Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}