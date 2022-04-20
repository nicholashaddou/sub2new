package com.example.submission1intermediate.ui

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1intermediate.data.response.ListStory
import com.example.submission1intermediate.databinding.ItemRowBinding
import com.example.submission1intermediate.ui.userDetail.UserDetailActivity

class ListAdapter(private val listStory : ArrayList<ListStory>): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private lateinit var binding: ItemRowBinding
    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ListViewHolder {

        binding = ItemRowBinding.inflate(LayoutInflater.from(view.context),view,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int {
        return listStory.size
    }

    inner class ListViewHolder(private var binding:ItemRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listStoryItem:ListStory){
            binding.apply {
                name.text = listStoryItem.name
                Glide.with(itemView.context)
                    .load(listStoryItem.photoUrl)
                    .into(imgStory)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, UserDetailActivity::class.java)
                    intent.putExtra(UserDetailActivity.EXTRA_DATA, listStoryItem)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imgStory,"imageStory"),
                            Pair(name,"nameStory")
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }
}