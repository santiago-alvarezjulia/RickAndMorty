package com.saj.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.saj.rickandmorty.R
import com.saj.rickandmorty.databinding.FragmentDetailBinding
import com.saj.rickandmorty.ui.imagemanager.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val showCharacter = args.showCharacter
        imageLoader.loadCircleImage(this, binding.characterAvatar, showCharacter.imageUrl)
        binding.characterName.text = getString(R.string.detail_fragment_name,
            showCharacter.name)
        binding.characterStatus.text = getString(R.string.detail_fragment_status,
            showCharacter.status)
        binding.characterEpisodes.text = getString(R.string.detail_fragment_episodes,
            showCharacter.episodesCount)
    }
}