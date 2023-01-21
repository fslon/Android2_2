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
private const val TEMP_INVALID = -100
private const val WIND_INVALID = -100.0
private const val HUMIDITY_INVALID = -100
private const val PROCESS_ERROR = "Обработка ошибки"

private const val REQUEST_API_KEY = "X-Yandex-API-Key"
private const val MAIN_LINK = "https://api.weather.yandex.ru/v2/informers?"


const val YOUR_API_KEY = "b8071b48-3dde-46cb-a01d-808d4e60bd46"

class DetailsFragment : Fragment() {


    private var _viewBinding: DetailsFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var weatherBundle: Weather

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }


//    private val loadResultsReceiver: BroadcastReceiver = object :
//        BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
//                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
//                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
//                    WeatherDTO(
//                        FactDTO(
//                            intent.getIntExtra(
//                                DETAILS_TEMP_EXTRA, TEMP_INVALID
//                            ),
//                            intent.getDoubleExtra(
//                                DETAILS_WIND_EXTRA,
//                                WIND_INVALID
//                            ),
//                            intent.getIntExtra(
//                                DETAILS_HUMIDITY_EXTRA,
//                                HUMIDITY_INVALID
//                            )
//                        )
//                    )
//                )
//                else -> TODO(PROCESS_ERROR)
//            }
//        }
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        context?.let {
//            LocalBroadcastManager.getInstance(it)
//                .registerReceiver(
//                    loadResultsReceiver,
//                    IntentFilter(DETAILS_INTENT_FILTER)
//                )
//        }
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = DetailsFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
//        getWeather()

        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getWeatherFromRemoteSource(
            MAIN_LINK +
                    "lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}"
        )
    }

//    private fun renderData(weatherDTO: WeatherDTO) {
//        viewBinding.main.visibility = View.VISIBLE
//        viewBinding.downloadingLayout.visibility = View.GONE
//        val fact = weatherDTO.fact
//        if (fact == null || fact.temp == null || fact.wind_speed == null || fact.humidity == null
//        ) {
//            Snackbar.make(
//                viewBinding.root,
//                "TEMP_INVALID, WIND_INVALID, HUMIDITY_INVALID ",
//                Snackbar.LENGTH_LONG
//            ).show()
//            TODO(PROCESS_ERROR)
//        } else {
//            val city = weatherBundle.city
//            viewBinding.textviewCityResult.text = city.city
//            viewBinding.textviewTemperatureResult.text = fact.temp.toString()
//            viewBinding.textviewWindResult.text = fact.wind_speed.toString()
//            viewBinding.textviewWetnessResult.text = "${fact.humidity}%"
//            viewBinding.textviewCoordinates.text =
//                "${getString(R.string.lat_lon)} ${city.lat}, ${city.lon}"
//        }
//    }

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
                            MAIN_LINK +
                                    "lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}"
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
//        context?.let {
//            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
//        }
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

























