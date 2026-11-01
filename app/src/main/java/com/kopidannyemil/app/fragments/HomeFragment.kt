package com.kopidannyemil.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopidannyemil.app.MainActivity
import com.kopidannyemil.app.R
import com.kopidannyemil.app.adapter.MenuAdapter
import com.kopidannyemil.app.data.AppState
import com.kopidannyemil.app.data.Category
import com.kopidannyemil.app.data.DummyData
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var filter: Category? = null
    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MenuAdapter(DummyData.menu) { updateCartBar() }
        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMenu.adapter = adapter

        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            filter = when (checkedIds.firstOrNull()) {
                R.id.chipMinuman -> Category.MINUMAN
                R.id.chipMakanan -> Category.MAKANAN
                else -> null
            }
            refresh()
        }

        binding.cartBar.setOnClickListener {
            (activity as? MainActivity)?.openOrderTab()
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
        binding.tvGreeting.text = "Halo ${AppState.displayName},"
        adapter.submit(DummyData.menu.filter { filter == null || it.category == filter })
        updateCartBar()
    }

    private fun updateCartBar() {
        if (_binding == null) return
        val count = AppState.cartCount
        binding.cartBar.visibility = if (count > 0) View.VISIBLE else View.GONE
        binding.tvCartCount.text = "$count item di keranjang"
        binding.tvCartTotal.text = AppState.cartTotal.toRupiah()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
