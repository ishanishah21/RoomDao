package com.ishani.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.ishani.roomdemo.databinding.ActivityMainBinding
import com.ishani.roomdemo.db.SubscriberDAO
import com.ishani.roomdemo.db.SubscriberDataBase
import com.ishani.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
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

    private fun displaySubscriberList() {
        lifecycleScope.launch {
            viewModel.subscribers.flowWithLifecycle(lifecycle).asLiveData().observe(this@MainActivity, Observer {
                Log.i("MyTag", it.toString())
            })
        }
      /*  viewModel.subscribers.asLiveData().observe(this, Observer {
            Log.i("MyTag", it.toString())
        })*/

       /* viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.subscribers.collect {
                Log.i("MyTag", it.toString())
            }
        }*/
    }
}