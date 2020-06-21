package com.rileygale.ad340

interface AppNavigator {
    fun navigateToCurrentForecast(zipcode: String);
    fun navigateToLocationEntry()
}