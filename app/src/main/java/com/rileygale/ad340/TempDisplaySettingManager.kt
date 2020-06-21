package com.rileygale.ad340

import android.content.Context

enum class TempDisplaySetting {
    Fahrenheit, Celsius
}

class TempDisplaySettingManager(context: Context) {

    //allows us to open a file on the disc or create one to write out key value pairs of data that you want to persist across restarts
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    //pass in a TempDisplaySetting to indicate how we want the temp to be displayed
    fun updateSetting(setting: TempDisplaySetting) {

        //uses an edit mode to let us edit the preferences
        preferences.edit().putString("key_temp_display", setting.name).commit()
    }

    //allows us to retrieve the setting
    fun getTempDisplaySetting() : TempDisplaySetting {

        //val foo: String ~ this is a non-nullible type
        //val foo: String? ~ this allows for it to be nullible

        val settingValue = preferences.getString("key_temp_display", TempDisplaySetting.Fahrenheit.name) ?: TempDisplaySetting.Fahrenheit.name
        return TempDisplaySetting.valueOf(settingValue)
    }

}