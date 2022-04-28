package com.example.submission1intermediate.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1intermediate.R
import com.example.submission1intermediate.ViewModelFactory
import com.example.submission1intermediate.background.UserModel
import com.example.submission1intermediate.background.preference.UserPreference
import com.example.submission1intermediate.background.response.ListStory
import com.example.submission1intermediate.databinding.ActivityMainBinding
import com.example.submission1intermediate.ui.AddStory.AddStoryActivity
import com.example.submission1intermediate.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var userModel: UserModel
    private lateinit var adapter: Adapter

    //TODO MAKE ADAPTER
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupViewModel()
        setAdapter()
        isLoading(true)
        //setupViewModel()
        //validateMain()

    }

//    private fun validateMain(){
////        mainViewModel.listUser.observe(this) {
////            setAdapter(it)
////            isLoading(false)
////        }
//
////        mainViewModel.getUser().observe(this) { user ->
////            if (user.isLogin) {
////                user.token.let { mainViewModel.getListStory(user.token) }
////            }
////        }
////        mainViewModel.isLoading.observe(this) {
////            isLoading(it)
////        }
////
////        mainViewModel.apiResponse.observe(
////            this,
////        ) {
////            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
////        }
//    }

//    private fun setAdapter(listStory : ArrayList<ListStory>){
//        val adapter = ListAdapter(listStory)
//        binding.apply {
//            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
//            rvStory.setHasFixedSize(true)
//            rvStory.adapter = adapter
//        }
//    }

    private fun setAdapter(){
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvStory.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvStory.layoutManager = LinearLayoutManager(this)
        }
        adapter = Adapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(footer = LoadingStateAdapter {
            adapter.retry()
        })
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            this.userModel = user
            if (user.isLogin) {
                isLoading(false)
                supportActionBar?.title = "${user.name}'s home page"
                mainViewModel.pagingStory(user.token).observe(this){
                    adapter.setList(it)
                    adapter.submitData(lifecycle, it)
                }
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout->{
                mainViewModel.logout()
            }
            R.id.add->{
                mainViewModel.getUser().observe(this) { user ->
                    Intent(this, AddStoryActivity::class.java).let {
                        it.putExtra(AddStoryActivity.EXTRA_TOKEN, user.token)
                        startActivity(it)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isLoading(value:Boolean){
        Log.d(TAG, "isLoading: $value")
        if (value){
            binding.rvStory.visibility = View.GONE
            binding.progressBarMain.visibility = View.VISIBLE
        }else{
            binding.progressBarMain.visibility = View.GONE
            binding.rvStory.visibility = View.VISIBLE
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        const val EXTRA_TOKEN = "extra_token"
    }
}