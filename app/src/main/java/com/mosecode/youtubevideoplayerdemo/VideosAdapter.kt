package com.mosecode.youtubevideoplayerdemo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mosecode.youtubevideoplayerdemo.SearchResponse.Item
import com.mosecode.youtubevideoplayerdemo.databinding.VideoRowBinding

class VideosAdapter(private val onClickListener: OnClickListener) : ListAdapter<Item, VideosAdapter.MenuViewHolder>(MenuDiffUtil) {
    inner class MenuViewHolder(private val binding: VideoRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Item?) {
            binding.textViewId.text = "YOUTUBE_VIDEO_ID: ${item?.id?.videoId}"
        }
    }

    object MenuDiffUtil : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            VideoRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    class OnClickListener(val clickListener: (item: Item) -> Unit) {
        fun onClick(item: Item) = clickListener(item)
    }
}