package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.kopidannyemil.app.data.AppState
import com.kopidannyemil.app.databinding.ActivityRegisterBinding
import com.kopidannyemil.app.util.openMainAndClear

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.tvGoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.etFullName.doAfterTextChanged { binding.tvError.visibility = View.GONE }
        binding.etUsername.doAfterTextChanged { binding.tvError.visibility = View.GONE }
        binding.etPassword.doAfterTextChanged { binding.tvError.visibility = View.GONE }

        binding.btnRegister.setOnClickListener {
            val error = AppState.register(
                binding.etFullName.text.toString(),
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )
            if (error == null) {
                openMainAndClear()
            } else {
                binding.tvError.text = error
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }
}
