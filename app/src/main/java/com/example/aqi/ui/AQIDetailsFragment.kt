package com.example.aqi.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.aqi.R
import com.example.aqi.data.AQIViewModel
import com.example.aqi.databinding.DetailsLayoutBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AQIDetailsFragment : Fragment(R.layout.details_layout) {
    private val args by navArgs<AQIDetailsFragmentArgs>()
    private val viewModel by viewModels<AQIViewModel>()
    private val entries = ArrayList<BarEntry>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DetailsLayoutBinding.bind(view)
        binding.apply {
            val city = args.cityName
            cityNameText.text = city
        }
        lifecycleScope.launch {
            viewModel.receive().collect {
                val cityAqi = it.filter { aqi -> aqi.city == args.cityName }
                if (cityAqi.isNotEmpty()) {
                    val roundedAqi: String = "%.2f".format(cityAqi[0].aqi)
                    val pos = entries.size
                    val barEntry = BarEntry(roundedAqi.toFloat(), pos.toFloat())
                    showBarChart(barEntry, binding)
                    binding.aqiText.text = roundedAqi
                }
            }
        }
    }

    private fun showBarChart(barEntry: BarEntry, binding: DetailsLayoutBinding) {
        entries.add(barEntry)
        val barDataSet = BarDataSet(entries, "")
        initBarDataSet(barDataSet)
        val barData = BarData(barDataSet)
        barData.barWidth = 0.6f
        binding.apply {
            binding.barChart.data = barData
            barChart.setDrawGridBackground(false)
            barChart.setDrawBarShadow(false)
            barChart.setDrawBorders(false)
            barChart.setTouchEnabled(false)
            val description = Description()
            description.isEnabled = false
            barChart.description = description
            barChart.legend.isEnabled = false
            binding.barChart.invalidate()
        }


    }

    private fun initBarDataSet(barDataSet: BarDataSet) {
        barDataSet.formSize = 15f
        barDataSet.setDrawValues(false)
        barDataSet.valueTextSize = 12f
    }

}