package ru.geekbrains.android2_2.view.details

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.coroutines.Runnable
import ru.geekbrains.android2_2.R
import ru.geekbrains.android2_2.databinding.DetailsFragmentBinding
import ru.geekbrains.android2_2.model.Weather
import ru.geekbrains.android2_2.model.WeatherDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val YOUR_API_KEY = "b8071b48-3dde-46cb-a01d-808d4e60bd46"

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
        loadWeather()


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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadWeather() {
        try {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}")
            val handler = Handler()

            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty("X-Yandex-API-Key", YOUR_API_KEY)
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val weatherDTO: WeatherDTO =
                        Gson().fromJson(getLines(bufferedReader), WeatherDTO::class.java)
                    handler.post { displayWeather(weatherDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            }).start()


        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
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

























