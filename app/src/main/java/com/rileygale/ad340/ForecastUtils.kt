package com.rileygale.ad340

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTempForDisplay(temp: Float, tempDisplaySetting: TempDisplaySetting): String {
    //return String.format("%.2f°", temp) //formats to 2 decimal places and returns that formatted string
    return when (tempDisplaySetting){
        TempDisplaySetting.Fahrenheit -> String.format("%.2f F°", temp)
        TempDisplaySetting.Celsius -> {
            val temp = (temp - 32f) * (5f/9f)
                    String.format("%.2f C°", temp)
        }
    }
}


fun showTempDisplaySettingDialog(context: Context, tempDisplaySettingsManager: TempDisplaySettingManager) {
    //display an alert dialog
    val dialogBuilder = AlertDialog.Builder(context) //this returns an instance of the builder ~ FLUENT API
        .setTitle("Choose Display Units")
        .setMessage("Choose which temperature unit to use for display")
        .setPositiveButton("F°") { _, _ -> //using a lambda for the clock listener
            //Toast.makeText(this, "show using F°", Toast.LENGTH_SHORT).show()
            tempDisplaySettingsManager.updateSetting((TempDisplaySetting.Fahrenheit))
        }
        .setNeutralButton("C°") { _, _ -> //using a lambda for the clock listener
            //Toast.makeText(this, "show using C°", Toast.LENGTH_SHORT).show()
            tempDisplaySettingsManager.updateSetting((TempDisplaySetting.Celsius))
        }
        .setOnDismissListener {
            Toast.makeText(context, "Setting will take effect on app restart", Toast.LENGTH_SHORT)
        }
    //creates an instance of the dialogue and calls "show" on it
    dialogBuilder.show()
}


