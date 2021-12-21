package com.ishani.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ishani.roomdemo.databinding.ActivityMainBinding
import com.ishani.roomdemo.db.SubscriberDAO
import com.ishani.roomdemo.db.SubscriberDataBase
import com.ishani.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDataBase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val subscriberViewModelFactory = SubscriberViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, subscriberViewModelFactory).get(SubscriberViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        viewModel.viewModelScope.launch {
            viewModel.subscribers.collect {
                Log.i("MyTag", it.toString())
            }
        }
    }
}