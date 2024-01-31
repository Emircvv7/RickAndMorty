package com.example.mycalck.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mycalck.R
import com.example.mycalck.character.CharacterZ
import com.example.mycalck.databinding.ActivityCharacterDetailBinding
import com.example.mycalck.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCharacterDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val characterId = intent.getIntExtra("characterId", 0)
        viewModel.loadCharacter(characterId)

        viewModel.character.observe(this) { state ->
            when (state) {
                is Resource.Error -> {
                    binding.progressBar2.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    binding.progressBar2.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar2.visibility = View.GONE
                    state.data?.let { character ->
                        updateUI(character)
                    }
                }
            }
        }
    }

    private fun updateUI(character: CharacterZ) {
        binding.characterName.text = character.name
        binding.characterStatusAndSpecies.text =
            "${character.status} - ${character.species}"
        binding.characterGender.text = character.gender
        binding.characterLastKnownLocation.text = character.location.name
        binding.characterFirstSeenIn.text = character.origin.name
        Glide.with(binding.root).load(character.image).into(binding.characterImage)

        when (character.status) {
            "Alive" -> binding.statusIndicator.setBackgroundResource(R.drawable.status_alive)
            "Dead" -> binding.statusIndicator.setBackgroundResource(R.drawable.status_dead)
            else -> binding.statusIndicator.setBackgroundResource(R.drawable.status_unknown)
        }

        val adapter = EpisodeListAdapter()
        binding.recyclerView1.adapter = adapter
        binding.recyclerView1.layoutManager = LinearLayoutManager(this)
        binding.recyclerView2.adapter = adapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        adapter.submitList(listOf(character))
    }
}
