package com.example.submission1intermediate.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submission1intermediate.R
import com.example.submission1intermediate.ViewModelFactory
import com.example.submission1intermediate.data.UserModel
import com.example.submission1intermediate.data.preference.UserPreference
import com.example.submission1intermediate.databinding.ActivityLoginBinding
import com.example.submission1intermediate.ui.MainActivity
import com.example.submission1intermediate.ui.register.RegisterActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupViewModel()
        supportActionBar?.hide()

        validateLogin()

        playActivity()
    }

    private fun validateLogin(){
        loginViewModel.isLoading.observe(this) {
            isLoading(it)
        }
        loginViewModel.apiResponse.observe(this) { message ->
            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
        }

        loginViewModel.isEmailEmpty.observe(this) {
            if (it) binding.etEmail.error = getString(R.string.requires_email)
        }

        loginViewModel.isPasswordEmpty.observe(this) {
            if (it) binding.etPasswordLogin.error = getString(R.string.requires_password)
        }

        loginViewModel.isPasswordValid.observe(this) {
            if (!it) binding.etPasswordLogin.error = getString(R.string.password_error) else binding.etEmail.error =
                null
        }
    }

    private fun playActivity() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA,0f, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA,0f, 1f).setDuration(500)
        val emailTV = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA,0f, 1f).setDuration(500)
        val emailLayout = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA,0f, 1f).setDuration(500)
        val passwordTV = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA,0f, 1f).setDuration(500)
        val passwordLayout = ObjectAnimator.ofFloat(binding.etPasswordLogin, View.ALPHA,0f, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA,0f, 1f).setDuration(500)

        binding.daftarTextView.setOnClickListener {
            Intent(this, RegisterActivity::class.java).let {
                startActivity(it)
            }
        }

        AnimatorSet().apply {
            playSequentially(title, message, emailTV,emailLayout,passwordTV,passwordLayout,btnLogin)
            start()
        }
    }


    private fun setupViewModel(){
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) { user ->
            Log.d("berhasil Login: ", user.token)
            this.user = user

            Log.d("LoginActivity", "Token: ${user.token}")
            if (this.user.isLogin) {
                Intent(this@LoginActivity, MainActivity::class.java).let {
                    it.putExtra(MainActivity.EXTRA_TOKEN, user.token)
                    startActivity(it)
                }
            }
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPasswordLogin.text.toString()
            loginViewModel.loginUser(this,email,password)
        }
    }

    private fun isLoading(value:Boolean){
        if (value){
            binding.pgSignin.visibility = View.VISIBLE
            binding.loginButton.isEnabled = false
        }else{
            binding.pgSignin.visibility = View.GONE
            binding.loginButton.isEnabled = true
        }
    }
    companion object{
        val TAG ="ActivityLogin"
    }
}