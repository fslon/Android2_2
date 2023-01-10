package ru.geekbrains.android2_2.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.android2_2.R
import ru.geekbrains.android2_2.databinding.DetailsFragmentBinding
import ru.geekbrains.android2_2.model.*

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


const val YOUR_API_KEY = "b8071b48-3dde-46cb-a01d-808d4e60bd46"

class DetailsFragment : Fragment() {


    private var _viewBinding: DetailsFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var weatherBundle: Weather


    private val loadResultsReceiver: BroadcastReceiver = object :
        BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
                    WeatherDTO(
                        FactDTO(
                            intent.getIntExtra(
                                DETAILS_TEMP_EXTRA, TEMP_INVALID
                            ),
                            intent.getDoubleExtra(
                                DETAILS_WIND_EXTRA,
                                WIND_INVALID
                            ),
                            intent.getIntExtra(
                                DETAILS_HUMIDITY_EXTRA,
                                HUMIDITY_INVALID
                            )
                        )
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(
                    loadResultsReceiver,
                    IntentFilter(DETAILS_INTENT_FILTER)
                )
        }

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
        getWeather()
    }

    private fun getWeather() {
        viewBinding.main.visibility = View.GONE
        viewBinding.downloadingLayout.visibility = View.VISIBLE

        context?.let {
            it.startService(Intent(it, LoadWeatherService::class.java).apply {
                putExtra(
                    LATITUDE_EXTRA,
                    weatherBundle.city.lat
                )
                putExtra(
                    LONGITUDE_EXTRA,
                    weatherBundle.city.lon
                )
            })
        }
    }

    private fun renderData(weatherDTO: WeatherDTO) {
        viewBinding.main.visibility = View.VISIBLE
        viewBinding.downloadingLayout.visibility = View.GONE
        val fact = weatherDTO.fact
        val temp = fact!!.temp
        val wind = fact.wind_speed
        val humidity = fact.humidity
        if (temp == TEMP_INVALID || wind == WIND_INVALID || humidity ==
            HUMIDITY_INVALID
        ) {
            Snackbar.make(
                viewBinding.root,
                "TEMP_INVALID, WIND_INVALID, HUMIDITY_INVALID ",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            val city = weatherBundle.city
            viewBinding.textviewCityResult.text = city.city
            viewBinding.textviewTemperatureResult.text = temp.toString()
            viewBinding.textviewWindResult.text = wind.toString()
            viewBinding.textviewWetnessResult.text = "${humidity}%"
            viewBinding.textviewCoordinates.text =
                "${getString(R.string.lat_lon)} ${city.lat}, ${city.lon}"
        }
    }


    override fun onDestroy() {
        _viewBinding = null
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
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

























