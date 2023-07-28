package glez.pavlo.showcase.core.presentation.bottomFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import glez.pavlo.showcase.core.model.Result
import glez.pavlo.showcase.databinding.FragmentTechBinding
import glez.pavlo.showcase.feature_tech_stack.presentation.TechStackViewModel
import glez.pavlo.showcase.feature_tech_stack.presentation.utils.TechStackAdapter

@AndroidEntryPoint
class TechUsedFragment : Fragment() {

    private lateinit var binding: FragmentTechBinding
    private val viewModel: TechStackViewModel by viewModels()
    private val techAdapter = TechStackAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        doObserveWork()
    }

    private fun initViews() {
        binding.techRecyclerView.run {
            adapter = techAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun doObserveWork() {
        viewModel.techStackResponse.observe(viewLifecycleOwner) { techStackResponse ->
            when (techStackResponse) {
                is Result.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                    binding.techRecyclerView.visibility = View.GONE
                }

                is Result.Failure -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.techRecyclerView.visibility = View.GONE
                    //binding.errorTextView.visibility = View.VISIBLE
                    //binding.errorTextView.text = getString(R.string.error_message)
                }

                is Result.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.techRecyclerView.visibility = View.VISIBLE
                    //binding.errorTextView.visibility = View.GONE
                    (binding.techRecyclerView.adapter as TechStackAdapter).submitList(
                        techStackResponse.data.sortedWith(
                            compareBy(
                                { it.isImplemented.not() },
                                { it.name }
                            )
                        )
                    )
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getTechStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTechBinding.inflate(inflater, container, false)
        return binding.root
    }
}