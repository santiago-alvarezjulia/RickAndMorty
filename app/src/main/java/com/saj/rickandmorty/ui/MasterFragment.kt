package com.saj.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.saj.rickandmorty.databinding.FragmentMasterBinding
import com.saj.rickandmorty.models.ShowCharacter
import com.saj.rickandmorty.ui.adapters.ShowCharactersAdapter
import com.saj.rickandmorty.viewmodels.ShowCharactersMasterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MasterFragment : Fragment() {

    companion object {
        private const val LOAD_NEW_PAGE_THRESHOLD = 8
    }

    private val showCharactersMasterViewModel: ShowCharactersMasterViewModel by viewModels()

    @Inject
    lateinit var showCharactersAdapter: ShowCharactersAdapter

    private var _binding: FragmentMasterBinding? = null
    private val binding get() = _binding!!

    private var loadingNewPage = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentMasterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpShowCharactersAdapter()

        showCharactersMasterViewModel.showCharacterLiveData.observe(viewLifecycleOwner, {
            setCharactersAdapterData(it)
            loadingNewPage = false
        })
    }

    private fun setUpShowCharactersAdapter() {
        val layoutManager = LinearLayoutManager(activity)
        binding.charactersList.layoutManager = layoutManager
        showCharactersAdapter.setHasStableIds(true)
        binding.charactersList.adapter = showCharactersAdapter
        binding.charactersList.setHasFixedSize(true)

        binding.charactersList.setOnScrollChangeListener { _, _, _, _, _ ->
            val itemCount = showCharactersAdapter.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            if (lastVisibleItemPosition == itemCount - LOAD_NEW_PAGE_THRESHOLD && !loadingNewPage) {
                loadingNewPage = true
                showCharactersMasterViewModel.loadNewShowCharactersPage()
            }
        }
    }

    private fun setCharactersAdapterData(showCharacters: List<ShowCharacter>) {
        showCharactersAdapter.setData(showCharacters)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}