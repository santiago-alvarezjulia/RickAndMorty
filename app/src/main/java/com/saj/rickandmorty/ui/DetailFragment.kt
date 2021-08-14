package com.saj.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.saj.rickandmorty.databinding.FragmentDetailBinding
import com.saj.rickandmorty.models.ShowCharacter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val showCharacterName = "Rick Sanchez"
    private val showCharacterStatus = "Alive"
    private val showCharacterEpisodesCount = 2
    private val showCharacter = ShowCharacter(1, showCharacterName,
        showCharacterStatus, "image_url", showCharacterEpisodesCount)

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.characterName.text = showCharacter.name
        binding.characterStatus.text = showCharacter.status
    }
}