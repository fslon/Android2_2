package ru.geekbrains.android2_2.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.android2_2.R
import ru.geekbrains.android2_2.databinding.MainFragmentBinding
import ru.geekbrains.android2_2.model.Weather
import ru.geekbrains.android2_2.utils.showSnackBar
import ru.geekbrains.android2_2.view.details.DetailsFragment
import ru.geekbrains.android2_2.viewModel.AppState
import ru.geekbrains.android2_2.viewModel.MainViewModel

private const val IS_WORLD_KEY = "LIST_OF_TOWNS_KEY"

class MainFragment : Fragment() {

    private var isDataSetWorld: Boolean = false

    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
    val editor = sharedPref?.edit()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    //    private var isDataSetRus: Boolean = true
    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {

            activity?.supportFragmentManager?.apply {

                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                beginTransaction().replace(R.id.container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("").commitAllowingStateLoss()
            }

        }
    })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.d("******************", isDataSetWorld.toString())
//        Log.d("//////////////////", isDataSetWorld.toString())
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {                                                 //TODO NOW
//            editor?.putBoolean(IS_WORLD_KEY, !isDataSetWorld)
            changeWeatherDataSet()
        }
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it as AppState) })
//        viewModel.getWeatherFromLocalSourceRus()

        binding.buttonReload.setOnClickListener { showListOfTowns() }

        showListOfTowns()

    }

    private fun showListOfTowns() {
        activity?.let {
            isDataSetWorld = it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_WORLD_KEY, false)
            if (isDataSetWorld) {
                viewModel.getWeatherFromLocalSourceWorld()
                binding.mainFragmentFAB.setImageResource(R.drawable.world)
            } else {
                viewModel.getWeatherFromLocalSourceRus()
                binding.mainFragmentFAB.setImageResource(R.drawable.russia)
            }

        }
    }

    private fun changeWeatherDataSet() {
        isDataSetWorld = !isDataSetWorld
        saveListOfTowns()
        showListOfTowns()
    }

    private fun saveListOfTowns() {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(IS_WORLD_KEY, isDataSetWorld)
                apply()
            }
        }
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.recyclerMainParent.showSnackBar(getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getWeatherFromLocalSourceRus() })
            }
        }
    }


    override fun onDestroy() {
        _binding = null
        adapter.removeListener()
        super.onDestroy()
    }


    companion object {
        fun newInstance() = MainFragment()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }
}