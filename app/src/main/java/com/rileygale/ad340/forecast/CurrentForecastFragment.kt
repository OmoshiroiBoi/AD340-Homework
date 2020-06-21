package com.rileygale.ad340.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rileygale.ad340.*
import com.rileygale.ad340.details.ForecastDetailsActivity
import kotlinx.android.synthetic.main.fragment_current_forecast.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingsManager: TempDisplaySettingManager

    private lateinit var appNavigator: AppNavigator //defining a non-nullible variable that will be initializaed

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempDisplaySettingsManager = TempDisplaySettingManager(requireContext())

        val zipcode = arguments!!.getString(KEY_ZIPCODE) ?: ""//we're getting the bundle and looking for the value in it || !! will crash program if values are null

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        val locationEntryButton : FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            appNavigator.navigateToLocationEntry()
        }


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
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

        forecastRepository.loadForecast(zipcode)


        return view
    }

    private fun showForecastDetails(forecast: DailyForecast) {
        val forecastDetailsIntent = Intent(requireContext(), ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp", forecast.temp)
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailsIntent)
    }

    companion object { //object scoped to an instance of CurrentForecastFragment
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String) : CurrentForecastFragment {
            val fragment = CurrentForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode) //defined what values will be put in the bundle
            fragment.arguments = args

            return fragment
        }
    }

}