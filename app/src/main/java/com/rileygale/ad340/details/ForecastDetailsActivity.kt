package com.rileygale.ad340.details

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rileygale.ad340.*

class ForecastDetailsActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingsManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        tempDisplaySettingsManager = TempDisplaySettingManager(this)

        setTitle(R.string.forecast_details)

        val tempText = findViewById<TextView>(R.id.tempText)
        val descriptionText = findViewById<TextView>(R.id.descriptionText)

        val temp = intent.getFloatExtra("key_temp", 0f)
        //tempText.text = "${intent.getFloatExtra("key_temp",0f)}°"
        tempText.text = formatTempForDisplay(temp, tempDisplaySettingsManager.getTempDisplaySetting())
        descriptionText.text = intent.getStringExtra("key_description")
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
