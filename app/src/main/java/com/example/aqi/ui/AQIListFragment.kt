package com.example.aqi.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.aqi.R
import com.example.aqi.data.AQIViewModel
import com.example.aqi.databinding.ListLayoutBinding
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AQIListFragment : Fragment(R.layout.list_layout), AQIListAdapter.OnItemClickListener {

    private val viewModel by viewModels<AQIViewModel>()
    private var _binding: ListLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AQIListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ListLayoutBinding.bind(view)
        adapter = AQIListAdapter(this)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            binding.recyclerView.adapter = adapter
        }
        initObserver()
        lifecycleScope.launch {
            viewModel.receive().collect {
                for (item in it) {
                    item.updatedAt = System.currentTimeMillis()

                }
                adapter.submitList(it)


            }
        }

    }

    fun initObserver() {
        lifecycleScope.launch {
            viewModel.observeWebSocketEvent().collect {
                when (it) {
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        Log.d(tag, "connection open")

                    }
                    is WebSocket.Event.OnMessageReceived -> Log.d(tag, "Message received")
                    is WebSocket.Event.OnConnectionClosing -> Log.d(tag, "Connection closing")
                    is WebSocket.Event.OnConnectionClosed -> Log.d(tag, "Connection closed")
                    is WebSocket.Event.OnConnectionFailed -> Log.d(tag, "Connection failed")
                }

            }
        }


    }

    override fun onItemClick(city: String) {
        val action = AQIListFragmentDirections.actionAQIListFragmentToAQIDetailsFragment(city)
        findNavController().navigate(action)
    }
}