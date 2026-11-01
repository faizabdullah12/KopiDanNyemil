package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.kopidannyemil.app.data.AppState
import com.kopidannyemil.app.databinding.ActivityLoginBinding
import com.kopidannyemil.app.util.openMainAndClear

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.tvGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.etUsername.doAfterTextChanged { binding.tvError.visibility = View.GONE }
        binding.etPassword.doAfterTextChanged { binding.tvError.visibility = View.GONE }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val error = AppState.login(username, password)
            if (error == null) {
                openMainAndClear()
            } else {
                binding.tvError.text = error
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }
}
