package ru.startandroid.develop.twichapptest.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.startandroid.develop.twichapptest.R
import ru.startandroid.develop.twichapptest.databinding.TwitchItemBinding
import ru.startandroid.develop.twichapptest.model.remote.GameItem

class GamesAdapter : PagingDataAdapter<GameItem, GamesAdapter.GamesViewHolder>(GameComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val binding =
            TwitchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GamesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class GamesViewHolder(private val binding: TwitchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gameItem: GameItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(gameItem.game?.logo?.large)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_cancel)
                    .into(gameLogo)

                viewersCounter.text = gameItem.viewers.toString()
                nameOfTheGame.text = gameItem.game?.name
                channelsCounter.text = gameItem.channels.toString()
            }
        }
    }

    class GameComparator : DiffUtil.ItemCallback<GameItem>() {
        override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem) =
            oldItem.game?.id == newItem.game?.id

        override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem) =
            oldItem == newItem
    }
}