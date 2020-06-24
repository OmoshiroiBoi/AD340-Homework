package com.rileygale.ad340.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import coil.api.load
import com.rileygale.ad340.*
import com.rileygale.ad340.databinding.FragmentForecastDetailsBinding
import kotlinx.android.synthetic.main.item_daily_forecast.*
import java.text.SimpleDateFormat
import java.util.*

class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()

    private lateinit var viewModelFactory: ForecastDetailsViewModelFactory
    private val viewModel: ForecastDetailsViewModel by viewModels(
        factoryProducer = {viewModelFactory}
    )

    private var _binding:FragmentForecastDetailsBinding? = null

    //this property only valid between onCreateView and onDestroyView
    private val binding:FragmentForecastDetailsBinding get() = _binding!!

    private lateinit var tempDisplaySettingsManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        viewModelFactory = ForecastDetailsViewModelFactory(args)
        tempDisplaySettingsManager = TempDisplaySettingManager(requireContext())

        //val tempText = layout.findViewById<TextView>(R.id.tempText)
        //val descriptionText = layout.findViewById<TextView>(R.id.descriptionText)
        //add date and icon
        //val dateText = layout.findViewById<TextView>(R.id.dateText)
        //val forecastIcon = layout.findViewById<ImageView>(R.id.forecastIcon)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<ForecastDetailsViewState> { viewState ->
            //update the UI
            binding.tempText.text = formatTempForDisplay(viewState.temp, tempDisplaySettingsManager.getTempDisplaySetting())
            binding.descriptionText.text = viewState.description
            binding.dateText.text = viewState.date
            binding.forecastIcon.load(viewState.iconUrl)
        }
        viewModel.viewState.observe(viewLifecycleOwner,viewStateObserver)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
