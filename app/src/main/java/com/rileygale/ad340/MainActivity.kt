package com.rileygale.ad340

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rileygale.ad340.details.ForecastDetailsActivity
import com.rileygale.ad340.forecast.CurrentForecastFragment
import com.rileygale.ad340.location.LocationEntryFragment

class MainActivity : AppCompatActivity(),AppNavigator {


    private lateinit var tempDisplaySettingsManager: TempDisplaySettingManager

    //region Setup Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingsManager = TempDisplaySettingManager(this)

        supportFragmentManager //begin a new fragment transaction so we can add a fragment, specify an add call to
                               //create a new LocationEntryFragment and add it
            .beginTransaction()
            //.add(R.id.root,LocationEntryFragment()) //the add call needs to specify where to add the fragment
            .add(R.id.fragmentContainer, LocationEntryFragment())
            .commit()
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

    override fun navigateToCurrentForecast(zipcode: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, CurrentForecastFragment.newInstance(zipcode)) //call new instance method from companion object
            .commit()
    }

    override fun navigateToLocationEntry() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, LocationEntryFragment()) //call new instance method from companion object
            .commit()
    }

//    private fun showForecastDetails(forecast: DailyForecast) {
//        val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)
//        forecastDetailsIntent.putExtra("key_temp", forecast.temp)
//        forecastDetailsIntent.putExtra("key_description", forecast.description)
//        startActivity(forecastDetailsIntent)
//    }



    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    //endregion Setup Methods

    //region Teardown Methods

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    //endregion Teardown Methods
}
