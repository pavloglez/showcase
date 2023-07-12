package glez.pavlo.showcase.core.presentation.bottomFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import glez.pavlo.showcase.R
import glez.pavlo.showcase.databinding.FragmentJokesBinding

@AndroidEntryPoint
class JokesFragment : Fragment() {
    private lateinit var binding: FragmentJokesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJokesBinding.inflate(inflater, container, false)
        return binding.root
    }
}