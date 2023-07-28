package glez.pavlo.showcase.core.presentation.bottomFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import glez.pavlo.showcase.core.model.Result
import glez.pavlo.showcase.databinding.FragmentSkillsBinding
import glez.pavlo.showcase.feature_dev_profile.presentation.DevProfileViewModel
import glez.pavlo.showcase.feature_dev_profile.presentation.utils.SkillsAdapter

@AndroidEntryPoint
class SkillsFragment : Fragment() {

    private lateinit var binding: FragmentSkillsBinding
    private val viewModel: DevProfileViewModel by viewModels()
    private val skillsAdapter = SkillsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        doObserveWork()

    }

    private fun initViews() {
        binding.skillsRecyclerView.run {
            adapter = skillsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSkillsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun doObserveWork() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getSkills()
        }
        viewModel.skills.observe(viewLifecycleOwner) { skillsResult ->
            when (skillsResult) {
                is Result.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }

                is Result.Failure -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                is Result.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    (binding.skillsRecyclerView.adapter as SkillsAdapter).submitList(skillsResult.data.sortedByDescending { it.strength })
                }
            }
        }
    }
}