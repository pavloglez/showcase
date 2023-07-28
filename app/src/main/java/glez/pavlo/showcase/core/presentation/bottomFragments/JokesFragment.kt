package glez.pavlo.showcase.core.presentation.bottomFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import glez.pavlo.showcase.core.Constants.DEV_PHOTO_URL
import glez.pavlo.showcase.databinding.FragmentJokesBinding
import glez.pavlo.showcase.feature_jokes.presentation.utils.JokesAdapter
import glez.pavlo.showcase.feature_jokes.presentation.viewmodel.JokesViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JokesFragment : Fragment() {
    private lateinit var binding: FragmentJokesBinding
    private val viewModel: JokesViewModel by viewModels()
    private val jokeAdapter = JokesAdapter()
    private val devUrl by lazy { arguments?.getString(DEV_PHOTO_URL) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJokesBinding.inflate(inflater, container, false)
        initViews()
        lifecycleScope.launch {
            viewModel.jokesFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { jokes ->
                    jokeAdapter.submitList(jokes)
                    binding.apply {
                        jokeChatRecyclerView.smoothScrollToPosition(jokeAdapter.itemCount)
                        progressLoader.visibility = View.GONE
                    }
                }
        }
        return binding.root
    }

    private fun initViews() {
        jokeAdapter.profilePhotoUrl = devUrl
        binding.jokeChatRecyclerView.apply {
            adapter = jokeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.replyButton.setOnClickListener {
            binding.progressLoader.visibility = View.VISIBLE
            viewModel.getJokes()
        }
    }
}