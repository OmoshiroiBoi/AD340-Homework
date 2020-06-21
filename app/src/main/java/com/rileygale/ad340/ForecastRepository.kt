package com.rileygale.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {

    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast

    fun loadForecast(zipcode: String) {
        val randomValues = List(10) { Random.nextFloat().rem(100) * 100}
        val forecastItems = randomValues.map{temp ->
            DailyForecast(temp, getTempDescription(temp))
        }
        _weeklyForecast.setValue(forecastItems)
    }

    private fun getTempDescription(temp: Float) : String {
        return when (temp){
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f) -> "Let's shred some gnar"
            in 32f.rangeTo(55f) -> "This is normal for us"
            in 55f.rangeTo(65f) -> "Washington perfect"
            in 65f.rangeTo(80f) -> "Swimmin time!"
            in 80f.rangeTo(90f) -> "It can't really be that hot, can it?"
            in 90f.rangeTo(100f) -> "OH GOD IT'S LIKE STANDING ON THE SUN"
            else -> "Does not compute"
        }

        //return if(temp <75) "It's too cold" else "It's great!"
    }
}