package ru.geekbrains.android2_2.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.android2_2.R
import ru.geekbrains.android2_2.databinding.DetailsFragmentBinding
import ru.geekbrains.android2_2.model.Weather
import ru.geekbrains.android2_2.utils.showSnackBar
import ru.geekbrains.android2_2.viewModel.AppState
import ru.geekbrains.android2_2.viewModel.DetailsViewModel

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_TEMP_EXTRA = "TEMPERATURE"
const val DETAILS_WIND_EXTRA = "WIND"
const val DETAILS_HUMIDITY_EXTRA = "HUMIDITY"
private const val MAIN_LINK = "https://api.weather.yandex.ru/v2/informers?"


class DetailsFragment : Fragment() {


    private var _viewBinding: DetailsFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var weatherBundle: Weather

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = DetailsFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        viewModel.getWeatherFromRemoteSource(
            weatherBundle.city.lat,
            weatherBundle.city.lon
        )

    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                viewBinding.main.visibility = View.VISIBLE
                viewBinding.downloadingLayout.visibility = View.GONE
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                viewBinding.main.visibility = View.GONE
                viewBinding.downloadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                viewBinding.main.visibility = View.VISIBLE
                viewBinding.downloadingLayout.visibility = View.GONE
                viewBinding.main.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getWeatherFromRemoteSource(
                            weatherBundle.city.lat,
                            weatherBundle.city.lon
                        )
                    })
            }
        }
    }

    private fun setWeather(weather: Weather) {
        val city = weatherBundle.city
        viewBinding.textviewCityResult.text = city.city
        viewBinding.textviewTemperatureResult.text = weather.temperature.toString()
        viewBinding.textviewWindResult.text = weather.wind.toString()
        viewBinding.textviewWetnessResult.text = "${weather.wetness}%"
        viewBinding.textviewCoordinates.text =
            "${getString(R.string.lat_lon)} ${city.lat}, ${city.lon}"
        viewBinding.textviewConditionResult.text = weather.condition
    }


    override fun onDestroy() {
        _viewBinding = null
        super.onDestroy()
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}

























