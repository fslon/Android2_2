//package ru.geekbrains.android2_2.view
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import com.google.android.material.snackbar.Snackbar
//import ru.geekbrains.android2_2.R
//import ru.geekbrains.android2_2.databinding.DetailsFragmentBinding
//import ru.geekbrains.android2_2.databinding.MainFragmentBinding
//import ru.geekbrains.android2_2.model.Weather
//import ru.geekbrains.android2_2.viewModel.AppState
//import ru.geekbrains.android2_2.viewModel.MainViewModel
//
//class DetailsFragment : Fragment() {
//
//    companion object {
//        fun newInstance() = DetailsFragment()
//    }
//
//    private var viewBinding: DetailsFragmentBinding? = null
//
//    private lateinit var viewModel: MainViewModel
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewBinding = DetailsFragmentBinding.bind(view)
//
//    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return inflater.inflate(R.layout.details_fragment, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//
////        val observer = Observer<Any> { renderData(it) }
////        viewModel.getData().observe(viewLifecycleOwner, observer)
//
//        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it as AppState) })
//        viewModel.getWeatherFromLocalSource()
//
//        viewBinding?.buttonReload?.setOnClickListener { viewModel.getWeatherFromLocalSource() }
//
//    }
//
//    private fun renderData(appState: AppState) {
////        Toast.makeText(context, "data: $data", Toast.LENGTH_LONG).show()
//
//        when (appState) {
//
//            is AppState.Success -> {
//                val weatherData = appState.weatherData
//                viewBinding?.downloadingLayout?.visibility = View.GONE
//                setData(weatherData)
//            }
//            is AppState.Loading -> {
//                viewBinding?.downloadingLayout?.visibility = View.VISIBLE
//            }
//            is AppState.Error -> {
//
//                viewBinding?.downloadingLayout?.visibility = View.GONE
//                Snackbar
//                    .make(viewBinding!!.main, "Error", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Reload") { viewModel.getWeatherFromLocalSource() }
//                    .show()
//
//
//            }
//
//
//        }
//
//    }
//
//    private fun setData(weatherData: Weather) {
//        viewBinding?.textviewCityResult?.text = weatherData.city.city
//        viewBinding?.textviewTemperatureResult?.text = weatherData.temperature.toString()
//        viewBinding?.textviewWindResult?.text = weatherData.wind.toString()
//        viewBinding?.textviewWetnessResult?.text = weatherData.wetness.toString()
//
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        viewBinding = null
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
