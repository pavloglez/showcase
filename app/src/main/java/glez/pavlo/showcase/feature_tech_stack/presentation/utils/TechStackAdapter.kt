package glez.pavlo.showcase.feature_tech_stack.presentation.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import glez.pavlo.showcase.R
import glez.pavlo.showcase.databinding.TechListItemBinding
import glez.pavlo.showcase.feature_tech_stack.data.Tech

class TechStackAdapter : ListAdapter<Tech, TechViewHolder>(TechItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tech_list_item, parent, false)
        return TechViewHolder(view)
    }

    override fun onBindViewHolder(holder: TechViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class TechViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(tech: Tech) {
        TechListItemBinding.bind(itemView).apply {
            techName.text = tech.name
            techStatus.isChecked = tech.isImplemented
        }
    }
}

class TechItemDiffCallback : DiffUtil.ItemCallback<Tech>() {
    override fun areItemsTheSame(oldItem: Tech, newItem: Tech): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Tech, newItem: Tech): Boolean {
        return oldItem == newItem
    }

}