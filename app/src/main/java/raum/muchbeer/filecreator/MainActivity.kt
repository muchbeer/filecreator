package raum.muchbeer.filecreator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.filecreator.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.clientsFragment,
                R.id.addFragment,
                R.id.profileFragment
            )
        )

        setSupportActionBar(binding.toolbar)
      //  setupActionBarWithNavController(navController, appBarConfiguration)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        binding.bottomNavView.setupWithNavController(navController)

    }

    override fun onNavigateUp() : Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun hideKeyboard() {
        val view: View = binding.root
        val imm: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}