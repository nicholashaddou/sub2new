package com.example.submission1intermediate.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1intermediate.background.response.ListStory
import com.example.submission1intermediate.databinding.ItemRowBinding
import com.example.submission1intermediate.ui.userDetail.UserDetailActivity

@ExperimentalPagingApi
class Adapter : PagingDataAdapter<ListStory, Adapter.ViewHolder>(DIFF_CALLBACK) {

    private var stories: PagingData<ListStory>? = null

    fun setList(stories: PagingData<ListStory>?) {
        this.stories = stories
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val storyData = getItem(position)
        if (storyData != null) {
            holder.bind(storyData)
        }
    }

    class ViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: ListStory) {
            itemView.setOnClickListener {
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.imgStory, "storyImage"),
                        Pair(binding.name, "username")
                    )
                val intent = Intent(itemView.context, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_STORY, story)
                ContextCompat.startActivity(itemView.context, intent, optionsCompat.toBundle())
            }

            binding.apply {
                Glide.with(itemView)
                    .load(story.photoUrl)
                    .into(imgStory)
                name.text = story.name
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStory>() {
            override fun areItemsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}