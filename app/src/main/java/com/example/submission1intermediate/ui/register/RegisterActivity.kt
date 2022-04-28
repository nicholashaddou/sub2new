package com.example.submission1intermediate.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.submission1intermediate.R
import com.example.submission1intermediate.databinding.ActivityRegisterBinding
import com.example.submission1intermediate.ui.login.LoginActivity

@ExperimentalPagingApi
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        setupAction()
        playActivity()

        supportActionBar?.hide()

        binding.masuk.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        validateRegister()
    }

    private fun validateRegister(){
        registerViewModel.isLoading.observe(this) {
            isLoading(it)
            isLoadingIntent(it)
        }

        registerViewModel.apiResponse.observe(this) { mess ->
            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
        }

        registerViewModel.isNameEmpty.observe(this) {
            if (it) binding.nameEditTextLayout.error = getString(R.string.requires_name)
        }

        registerViewModel.isEmailEmpty.observe(this) {
            if (it) binding.etEmail.error = getString(R.string.requires_email)
        }

        registerViewModel.isPasswordEmpty.observe(this) {
            if (it) binding.etPassword.error = getString(R.string.requires_password)
        }

        registerViewModel.isPasswordValid.observe(this) {
            if (!it) binding.etPassword.error = getString(R.string.password_error) else binding.etEmail.error =
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
        val nameTV = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA,0f, 1f).setDuration(500)
        val nameET = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA,0f, 1f).setDuration(500)
        val emailTV = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA,0f, 1f).setDuration(500)
        val emailET = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA,0f, 1f).setDuration(500)
        val passwordTV = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA,0f, 1f).setDuration(500)
        val passwordET = ObjectAnimator.ofFloat(binding.etPassword, View.ALPHA,0f, 1f).setDuration(500)
        val btnSignup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA,0f, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, nameTV,nameET, emailTV,emailET,passwordTV,passwordET,btnSignup)
            start()
        }
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditTextLayout.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            registerViewModel.signUp(this,name,email,password)

        }
    }

    private fun isLoading(value:Boolean){
        if (value){
            binding.signupButton.isEnabled = false
            binding.pgSignup.visibility = View.VISIBLE
        }else{
            binding.signupButton.isEnabled = true
            binding.pgSignup.visibility = View.GONE
        }
    }

    private fun isLoadingIntent(value: Boolean){
        if (value){return}
        else{
            Intent(this, LoginActivity::class.java).let {
                startActivity(it)
            }
        }
    }
}