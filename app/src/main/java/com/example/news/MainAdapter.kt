package com.example.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.news.databinding.NewsitemBinding
import com.example.news.domain.NewsByte

class MainAdapter(private val OnitemClciked: (NewsByte) -> Unit): ListAdapter<NewsByte, MainAdapter.MainViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<NewsByte>() {
        override fun areItemsTheSame(oldItem: NewsByte, newItem: NewsByte): Boolean {
        return oldItem == newItem

        }

        override fun areContentsTheSame(oldItem: NewsByte, newItem: NewsByte): Boolean {
            return oldItem.title == newItem.title
        }
    }

    class MainViewHolder(private var binding: NewsitemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news:NewsByte){
            binding.category.text = news.title

            binding.newcorp.text = news.source
            if(news.image.isNullOrEmpty()){
                if (news.source.startsWith("C")){
                    binding.imageButton.setImageResource(R.drawable.cnn)
                }
                if (news.source.startsWith("B")){
                    binding.imageButton.setImageResource(R.drawable.bbc)
                }
                else{
                    binding.imageButton.setImageResource(R.drawable.ic_baseline_image_not_supported_24)

                }
            }
            else{
                binding.imageButton.load(news.image){
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_baseline_find_in_page_24)
                }
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
    return MainViewHolder(NewsitemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        holder.itemView.setOnClickListener {
            OnitemClciked(news)

        }
    }

}