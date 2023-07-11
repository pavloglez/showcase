package glez.pavlo.showcase.feature_dev_profile.presentation.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import glez.pavlo.showcase.R
import glez.pavlo.showcase.databinding.SkillsListItemBinding
import glez.pavlo.showcase.feature_dev_profile.data.Skill

class SkillsAdapter : ListAdapter<Skill, SkillsViewHolder>(SkillsItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillsViewHolder {
        return SkillsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.skills_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SkillsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SkillsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(skill: Skill) {
        SkillsListItemBinding.bind(itemView).apply {
            skillName.text = skill.name
            skillProgress.setProgress(skill.strength, true)
        }
    }
}

class SkillsItemDiffCallback : DiffUtil.ItemCallback<Skill>() {
    override fun areItemsTheSame(oldItem: Skill, newItem: Skill): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Skill, newItem: Skill): Boolean {
        return oldItem == newItem
    }
}