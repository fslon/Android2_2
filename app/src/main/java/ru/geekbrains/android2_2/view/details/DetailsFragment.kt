package ru.geekbrains.android2_2.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.details_fragment.*
import ru.geekbrains.android2_2.R
import ru.geekbrains.android2_2.databinding.DetailsFragmentBinding
import ru.geekbrains.android2_2.model.Weather
import ru.geekbrains.android2_2.model.WeatherDTO
import ru.geekbrains.android2_2.model.WeatherLoader

const val YOUR_API_KEY = "b8071b48-3dde-46cb-a01d-808d4e60bd46"

class DetailsFragment : Fragment() {


    private var _viewBinding: DetailsFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var weatherBundle: Weather

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = DetailsFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let { setData(it) }

        weatherBundle = arguments?.getParcelable<Weather>(BUNDLE_EXTRA) ?: Weather()
        viewBinding.main.visibility = View.GONE
        viewBinding.downloadingLayout.visibility = View.VISIBLE
        val loader = WeatherLoader(
            onLoadListener, weatherBundle.city.lat,
            weatherBundle.city.lon
        )
        loader.loadWeather()


    }

    private val onLoadListener: WeatherLoader.WeatherLoaderListener =
        object : WeatherLoader.WeatherLoaderListener {
            override fun onLoaded(weatherDTO: WeatherDTO) {
                displayWeather(weatherDTO)
            }

            override fun onFailed(throwable: Throwable) {
                Snackbar.make(viewBinding.root, "Ошибка", Snackbar.LENGTH_LONG).show()
            }
        }


    private fun displayWeather(weatherDTO: WeatherDTO) {

        with(viewBinding) {
            main.visibility = View.VISIBLE
            downloading_layout.visibility = View.GONE
            val city = weatherBundle.city
            textviewCityResult.text = city.city
            textviewTemperatureResult.text = weatherDTO.fact?.temp.toString()
            textviewWindResult.text = weatherDTO.fact?.wind_speed.toString()
            textviewWetnessResult.text = "${weatherDTO.fact?.humidity.toString()}%"

            textviewCoordinates.text = "${getString(R.string.lat_lon)} ${city.lat}, ${city.lon}"


        }

    }


    private fun setData(weatherData: Weather) {
        viewBinding.textviewCityResult.text = weatherData.city.city
        viewBinding.textviewTemperatureResult.text = weatherData.temperature.toString()
        viewBinding.textviewWindResult.text = weatherData.wind.toString()
        viewBinding.textviewWetnessResult.text = weatherData.wetness.toString()

    }


    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
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

























