package com.example.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.news.database.DatabaseNews
import com.example.news.databinding.NewsitemBinding
import com.example.news.domain.NewsByte

class MainAdapter(private val OnitemClciked: (DatabaseNews) -> Unit): PagingDataAdapter<DatabaseNews, MainAdapter.MainViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<DatabaseNews>() {
        override fun areItemsTheSame(oldItem: DatabaseNews, newItem: DatabaseNews): Boolean {
        return oldItem == newItem

        }

        override fun areContentsTheSame(oldItem: DatabaseNews, newItem: DatabaseNews): Boolean {
            return oldItem.title == newItem.title
        }
    }

    class MainViewHolder(private var binding: NewsitemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news:DatabaseNews) {
                binding.category.text = news.title

                binding.newcorp.text = "Source : ${news.source}"
//                if (news.images.isNullOrEmpty()) {
//                    if (news.source.startsWith("C")) {
//                        binding.imageButton.setImageResource(R.drawable.cnn)
//                    }
//                    if (news.source.contains("bbc", ignoreCase = true)) {
//                        binding.imageButton.setImageResource(R.drawable.bbc)
//                    }
//                    if (news.source.contains("bloomberg", ignoreCase = true)) {
//                        binding.imageButton.setImageResource(R.drawable.bloombergicon)
//                    } else {
//                        binding.imageButton.setImageResource(R.drawable.ic_baseline_newspaper_24)
//
//                    }
//                } else {
//                    binding.imageButton.load(news.images) {
//                        placeholder(R.drawable.loading_animation)
//                        error(R.drawable.ic_baseline_find_in_page_24)
//                    }
//                }

            binding.imageButton.load(news.images)


        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
    return MainViewHolder(NewsitemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        val news = getItem(position)
        if (news != null) {
            holder.bind(news)
        }

        holder.itemView.setOnClickListener {
            if (news != null) {
                OnitemClciked(news)
            }

        }
    }

}