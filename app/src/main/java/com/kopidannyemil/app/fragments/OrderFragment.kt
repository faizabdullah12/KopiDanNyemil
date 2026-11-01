package com.kopidannyemil.app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.kopidannyemil.app.ConfirmationActivity
import com.kopidannyemil.app.data.AppState
import com.kopidannyemil.app.databinding.FragmentOrderBinding
import com.kopidannyemil.app.util.bindOrderLines

/** Tab "Order": alamat pengiriman + ringkasan pesanan. */
class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.setText(AppState.currentUser?.fullName ?: "")
        binding.etName.doAfterTextChanged { updateButton() }
        binding.etAddress.doAfterTextChanged { updateButton() }

        binding.btnOrder.setOnClickListener {
            AppState.placeOrder(
                binding.etName.text.toString().trim(),
                binding.etAddress.text.toString().trim(),
                binding.etDetail.text.toString().trim()
            )
            binding.etAddress.text?.clear()
            binding.etDetail.text?.clear()
            refresh()
            startActivity(Intent(requireContext(), ConfirmationActivity::class.java))
        }

        refresh()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) refresh()
    }

    private fun refresh() {
        if (_binding == null) return
        val lines = AppState.cartLines
        val isEmpty = lines.isEmpty()
        binding.tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.llSummary.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.llSummary.bindOrderLines(lines, AppState.cartTotal)
        updateButton()
    }

    private fun updateButton() {
        if (_binding == null) return
        binding.btnOrder.isEnabled = AppState.cartCount > 0 &&
            !binding.etName.text.isNullOrBlank() &&
            !binding.etAddress.text.isNullOrBlank()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
