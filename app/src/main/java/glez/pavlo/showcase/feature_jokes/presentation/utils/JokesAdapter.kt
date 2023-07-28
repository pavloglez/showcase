package glez.pavlo.showcase.feature_jokes.presentation.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import glez.pavlo.showcase.R
import glez.pavlo.showcase.databinding.JokeListItemBinding
import glez.pavlo.showcase.feature_jokes.data.remote.RemoteJoke

class JokesAdapter : ListAdapter<RemoteJoke, JokeViewHolder>(JokesItemDiffCallback()) {

    var profilePhotoUrl: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.joke_list_item, parent, false)
        return JokeViewHolder(view)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position), profilePhotoUrl)
    }
}

class JokeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(joke: RemoteJoke, profilePhotoUrl: String?) {
        JokeListItemBinding.bind(itemView).apply {
            jokeMessage.text = "${joke.setup}\n${joke.punchline}"
            Glide.with(itemView.context)
                .load(profilePhotoUrl)
                .into(devProfilePhoto)
        }
    }
}

class JokesItemDiffCallback : DiffUtil.ItemCallback<RemoteJoke>() {
    override fun areItemsTheSame(oldItem: RemoteJoke, newItem: RemoteJoke): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RemoteJoke, newItem: RemoteJoke): Boolean {
        return oldItem == newItem
    }
}