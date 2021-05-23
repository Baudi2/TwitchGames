package ru.startandroid.develop.twichapptest.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.twichapptest.databinding.TwitchLoadStateFooterBinding

class TwitchLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<TwitchLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            TwitchLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: TwitchLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
              with(binding) {
                  footerProgressBar.isVisible = loadState is LoadState.Loading
                  buttonRetry.isVisible = loadState !is LoadState.Loading
                  footerTextViewError.isVisible = loadState !is LoadState.Loading
              }
        }
    }
}