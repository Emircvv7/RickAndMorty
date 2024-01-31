package com.example.mycalck.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycalck.character.CharacterZ
import com.example.mycalck.databinding.CharacterItemBinding

class CharacterAdapter(private val onCharacterClick: (CharacterZ) -> Unit) :
    ListAdapter<CharacterZ, CharacterAdapter.CharacterViewHolder>(CharacterDiffCallback()) {

    inner class CharacterViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val character = getItem(position)
                    onCharacterClick(character)
                }
            }
        }

        fun bind(character: CharacterZ) {
            binding.characterName.text = character.name
            binding.characterStatus.text = character.status
            binding.characterSpecies.text = character.species
            binding.characterType.text = character.type
            binding.characterGender.text = character.gender
            binding.characterOrigin.text = character.origin.name
            binding.characterLocation.text = character.location.name
            Glide.with(binding.root).load(character.image).into(binding.characterImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
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
