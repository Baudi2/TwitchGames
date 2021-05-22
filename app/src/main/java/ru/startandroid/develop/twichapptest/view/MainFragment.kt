package ru.startandroid.develop.twichapptest.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.startandroid.develop.twichapptest.R
import ru.startandroid.develop.twichapptest.databinding.MainFragmentBinding
import ru.startandroid.develop.twichapptest.viewModel.MainFragmentViewModel

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {
    private lateinit var binding: MainFragmentBinding
    private val viewModel by viewModels<MainFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)

        setHasOptionsMenu(true)

        val adapter = GamesAdapter()

        with(binding) {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }

        viewModel.games.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.rating -> {
                true
            }
            R.id.download_data -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}







