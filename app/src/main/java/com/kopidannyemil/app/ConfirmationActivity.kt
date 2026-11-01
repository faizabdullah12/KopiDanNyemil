package com.kopidannyemil.app

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.kopidannyemil.app.data.AppState
import com.kopidannyemil.app.databinding.ActivityConfirmationBinding
import com.kopidannyemil.app.util.bindOrderLines

/** Layar "Konfirmasi pesanan". */
class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvGreeting.text = "Halo ${AppState.displayName},"

        val order = AppState.lastOrder
        if (order == null) {
            binding.cardOrder.visibility = View.GONE
        } else {
            binding.tvReceiver.text = order.receiverName
            binding.tvAddress.text = order.address
            if (order.detail.isBlank()) {
                binding.tvDetail.visibility = View.GONE
            } else {
                binding.tvDetail.text = order.detail
            }
            binding.llLines.bindOrderLines(order.lines, order.total)
        }

        binding.btnOke.setOnClickListener { backToHome() }
        onBackPressedDispatcher.addCallback(this) { backToHome() }
    }

    private fun backToHome() {
        AppState.pendingHome = true
        finish()
    }
}
