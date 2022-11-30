package ru.geekbrains.android2_2.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.android2_2.databinding.DetailsFragmentBinding
import ru.geekbrains.android2_2.model.Weather

class DetailsFragment : Fragment() {


    private var _viewBinding: DetailsFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = DetailsFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let { setData(it) }

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

























