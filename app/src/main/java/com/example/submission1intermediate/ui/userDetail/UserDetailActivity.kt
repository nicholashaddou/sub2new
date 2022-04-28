package com.example.submission1intermediate.ui.userDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.Glide
import com.example.submission1intermediate.background.response.ListStory
import com.example.submission1intermediate.databinding.ActivityUserDetailBinding
import com.example.submission1intermediate.ui.MainActivity
import com.example.submission1intermediate.ui.map.MapsActivity

@ExperimentalPagingApi
class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //if does not work use extra_story
        val userData = intent.getParcelableExtra<ListStory>(EXTRA_STORY) as ListStory
        Glide.with(this)
            .load(userData.photoUrl)
            .into(binding.imageStory)
        binding.apply {
            tvName.text = userData.name
            tvDeskripsi.text = userData.description
        }

        binding.buttonBackToList.setOnClickListener{
            switchToListActivity()
        }
        binding.buttonToMaps.setOnClickListener {
            switchToMapsActivity()
        }

    }

    private fun switchToListActivity(){
        intent = Intent(this@UserDetailActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun switchToMapsActivity(){
        intent = Intent(this@UserDetailActivity, MapsActivity::class.java)
        startActivity(intent)
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_STORY = "extra_story"
    }
}