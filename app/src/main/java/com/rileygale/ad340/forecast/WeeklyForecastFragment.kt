package com.rileygale.ad340.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rileygale.ad340.*
import com.rileygale.ad340.details.ForecastDetailsFragment

/**
 * A simple [Fragment] subclass.
 * Use the [WeeklyForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeeklyForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingsManager: TempDisplaySettingManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)

        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""//we're getting the bundle and looking for the value in it || !! will crash program if values are null

        tempDisplaySettingsManager = TempDisplaySettingManager(requireContext())

        val dailyForecastList: RecyclerView = view.findViewById(R.id.dailyForecastList) //create new local variable for recycler )
        dailyForecastList.layoutManager = LinearLayoutManager(requireContext()) //LayoutManager informs recycler view how items will be laid out on the screen
        val dailyForecastListAdapter = DailyForecastListAdapter(tempDisplaySettingsManager) { forecast ->
            //val msg = getString(R.string.forecast_clicked_format, forecastItem.temp, forecastItem.description)
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            //val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)
            //startActivity(forecastDetailsIntent)
            showForecastDetails(forecast)
        }
        dailyForecastList.adapter = dailyForecastListAdapter


        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems ->
            //update our list adapter
            dailyForecastListAdapter.submitList(forecastItems)
        }
        forecastRepository.weeklyForecast.observe(viewLifecycleOwner, weeklyForecastObserver)

        val locationEntryButton : FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        forecastRepository.loadForecast(zipcode)

        return view
    }

    private fun showLocationEntry(){
        val action =WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun showForecastDetails(forecast: DailyForecast) {
        val action =WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(forecast.temp, forecast.description)
        findNavController().navigate(action)
    }

    companion object { //object scoped to an instance of CurrentForecastFragment
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String) : WeeklyForecastFragment {
            val fragment = WeeklyForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode) //defined what values will be put in the bundle
            fragment.arguments = args

            return fragment
        }
    }

}