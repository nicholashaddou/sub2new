package com.example.submission1intermediate.ui.userDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.submission1intermediate.data.response.ListStory
import com.example.submission1intermediate.databinding.ActivityUserDetailBinding
import com.example.submission1intermediate.ui.MainActivity

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val userData = intent.getParcelableExtra<ListStory>(EXTRA_DATA) as ListStory
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

    }

    private fun switchToListActivity(){
        intent = Intent(this@UserDetailActivity, MainActivity::class.java)
        startActivity(intent)
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
    }
}