package ru.geekbrains.android2_2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.android2_2.R
import ru.geekbrains.android2_2.databinding.MainFragmentBinding
import ru.geekbrains.android2_2.viewModel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var viewBinding: MainFragmentBinding? = null

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = MainFragmentBinding.bind(view)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

//        val observer = Observer<Any> { renderData(it) }
//        viewModel.getData().observe(viewLifecycleOwner, observer)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it as AppState) })
        viewModel.getWeather()

        viewBinding?.buttonReload?.setOnClickListener { viewModel.getWeather() }

    }

    private fun renderData(appState: AppState) {
//        Toast.makeText(context, "data: $data", Toast.LENGTH_LONG).show()

        when (appState) {

            is AppState.Success -> {
                val weatherData = appState.weatherData
                viewBinding?.downloadingLayout?.visibility = View.GONE
                Snackbar.make(viewBinding!!.main, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                viewBinding?.downloadingLayout?.visibility = View.VISIBLE
            }
            is AppState.Error -> {

                viewBinding?.downloadingLayout?.visibility = View.GONE
                Snackbar
                    .make(viewBinding!!.main, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeather() }
                    .show()


            }


        }

    }


    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

}

























