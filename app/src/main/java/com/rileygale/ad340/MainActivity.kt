package com.rileygale.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rileygale.ad340.forecast.CurrentForecastFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingsManager: TempDisplaySettingManager

    //region Setup Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingsManager = TempDisplaySettingManager(this)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph) //creates appBar configuration based on navigation graph
        findViewById<Toolbar>(R.id.toolbar).setTitle(R.string.app_name)
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true //indicates that yes we've handled this and we want to show the menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //handle item selection
        return when(item.itemId) {
            R.id.tempDisplaySetting -> {
                //Toast.makeText(this, "clicked menu item", Toast.LENGTH_SHORT).show()
                showTempDisplaySettingDialog(this, tempDisplaySettingsManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
