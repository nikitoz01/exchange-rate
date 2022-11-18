package kg.sl.rate.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.sl.rate.R
import kg.sl.rate.databinding.ActivityMainBinding
import kg.sl.rate.feature.rates.ui.RatesFragment
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            if(Build.VERSION_CODES.R < Build.VERSION.SDK_INT) {
                mainToolbar.setOnApplyWindowInsetsListener { view, insets ->
                    view.updatePadding(top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top)
                    insets
                }
            }

            setSupportActionBar(mainToolbar)
        }

        supportFragmentManager.commit {
            replace(R.id.nav_host_fragment_content_main, RatesFragment())
        }
    }


}