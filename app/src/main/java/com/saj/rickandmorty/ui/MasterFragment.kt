package com.saj.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.saj.rickandmorty.R
import com.saj.rickandmorty.viewmodels.ShowCharactersMasterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MasterFragment : Fragment() {

    private val showCharactersMasterViewModel: ShowCharactersMasterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_master, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showCharactersMasterViewModel.showCharacterLiveData.observe(viewLifecycleOwner, {

        })
    }
}