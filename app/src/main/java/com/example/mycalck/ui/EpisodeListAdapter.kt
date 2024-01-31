package com.example.mycalck.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycalck.character.CharacterZ
import com.example.mycalck.databinding.MySpinnerItemBinding

class EpisodeListAdapter : ListAdapter<CharacterZ, EpisodeListAdapter.ViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MySpinnerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    class ViewHolder(private val binding: MySpinnerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterZ) {
            binding.tvEpisodeName.text = character.name
            binding.tvEpisodeDate.text = character.created
        }
    }

    class CharacterDiffCallback : DiffUtil.ItemCallback<CharacterZ>() {
        override fun areItemsTheSame(oldItem: CharacterZ, newItem: CharacterZ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterZ, newItem: CharacterZ): Boolean {
            return oldItem == newItem
        }
    }
}
