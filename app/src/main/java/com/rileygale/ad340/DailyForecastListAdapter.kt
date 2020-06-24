package com.rileygale.ad340

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.rileygale.ad340.api.DailyForecast
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")
class DailyForecastViewHolder(
    view: View,
    private val tempDisplaySettingManager: TempDisplaySettingManager
    )
    : RecyclerView.ViewHolder(view){

    private val tempText: TextView = view.findViewById(R.id.tempText) //helps specify type
    private val descriptionText: TextView = view.findViewById(R.id.descriptionText)
    private val dateText = view.findViewById<TextView>(R.id.dateText)
    private val forecastIcon = view.findViewById<ImageView>(R.id.forecastIcon)

    fun bind(dailyForecast: DailyForecast) {
        //tempText.text = String.format("%.2f", dailyForecast.temp) //converts to a string and assigns it to a textView
        tempText.text = formatTempForDisplay(dailyForecast.temp.max, tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = dailyForecast.weather[0].description
        dateText.text = DATE_FORMAT.format(Date(dailyForecast.date * 1000))

        val iconId = dailyForecast.weather[0].icon
        forecastIcon.load(("http://openweathermap.org/img/wn/${iconId}@2x.png"))
    }
}

class DailyForecastListAdapter(
    private val tempDisplaySettingManager: TempDisplaySettingManager,
    private val clickHandler: (DailyForecast) -> Unit
) : ListAdapter<DailyForecast, DailyForecastViewHolder>(DIFF_CONFIG){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return DailyForecastViewHolder(itemView, tempDisplaySettingManager) //anytime the recylcerview needs to create a new viewHolder it will call this method
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickHandler(getItem(position)) //anytime the item is clicked it will execute this line
        }
    }

    companion object { //creating a new instance of an unnamed class
        val DIFF_CONFIG = object: DiffUtil.ItemCallback<DailyForecast>() {

            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem == newItem
            } //we are using a ":' to extend DiffUtil.ItemCallback


        }
    }
}

