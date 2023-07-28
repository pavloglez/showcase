package glez.pavlo.showcase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import glez.pavlo.showcase.core.Constants
import glez.pavlo.showcase.databinding.ActivityMainBinding
import glez.pavlo.showcase.core.model.Result
import glez.pavlo.showcase.feature_dev_profile.presentation.DevProfileViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val devProfileViewModel: DevProfileViewModel by viewModels()
    private lateinit var navController: NavController
    private var devUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        binding.navigationBar.setupWithNavController(navController)
        binding.navigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.jokesFragment -> {
                    val args = bundleOf(Constants.DEV_PHOTO_URL to devUrl)
                    navController.navigate(item.itemId, args)
                    true
                }

                else -> {
                    navController.navigate(item.itemId)
                    true
                }
            }
        }

        devProfileViewModel.devProfileResponse.observe(this) { devProfileResponse ->
            when (devProfileResponse) {
                is Result.Loading -> {
                    Toast.makeText(applicationContext, "Loading", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.apply {
                        devName.text = getString(
                            R.string.profile_full_name_template,
                            devProfileResponse.data.name,
                            devProfileResponse.data.lastNames
                        )
                        devRole.text = devProfileResponse.data.role
                        devDescription.text = devProfileResponse.data.description
                        devUrl = devProfileResponse.data.profilePhotoUrl
                        Glide.with(this@MainActivity)
                            .load(devUrl)
                            .placeholder(com.google.android.material.R.drawable.abc_spinner_mtrl_am_alpha)
                            .into(devProfilePhoto)
                    }
                }
                is Result.Failure -> {
                    Toast.makeText(applicationContext, "Error happened", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}