package com.kopidannyemil.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kopidannyemil.app.data.AppState
import com.kopidannyemil.app.databinding.ActivityMainBinding
import com.kopidannyemil.app.fragments.HomeFragment
import com.kopidannyemil.app.fragments.OrderFragment
import com.kopidannyemil.app.fragments.ProfileFragment

/** Halaman utama dengan bottom navigation: Home / Order / Profile. */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kalau belum login (misalnya proses sempat dimatikan sistem), balik ke awal.
        if (AppState.currentUser == null) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tiga fragment dibuat sekali lalu di-show/hide, supaya isi form Order
        // tidak hilang waktu pindah tab.
        if (savedInstanceState == null) {
            val home = HomeFragment()
            val order = OrderFragment()
            val profile = ProfileFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, home, TAG_HOME)
                .add(R.id.container, order, TAG_ORDER)
                .add(R.id.container, profile, TAG_PROFILE)
                .hide(order)
                .hide(profile)
                .commit()
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> showTab(TAG_HOME)
                R.id.nav_order -> showTab(TAG_ORDER)
                R.id.nav_profile -> showTab(TAG_PROFILE)
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        if (!::binding.isInitialized) return
        // Setelah halaman konfirmasi ditutup, kembali ke tab Home.
        if (AppState.pendingHome) {
            AppState.pendingHome = false
            binding.bottomNav.selectedItemId = R.id.nav_home
        }
    }

    /** Dipanggil dari HomeFragment saat bar keranjang diklik. */
    fun openOrderTab() {
        binding.bottomNav.selectedItemId = R.id.nav_order
    }

    private fun showTab(tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        listOf(TAG_HOME, TAG_ORDER, TAG_PROFILE).forEach { t ->
            val fragment = supportFragmentManager.findFragmentByTag(t) ?: return@forEach
            if (t == tag) transaction.show(fragment) else transaction.hide(fragment)
        }
        transaction.commit()
    }

    companion object {
        const val TAG_HOME = "home"
        const val TAG_ORDER = "order"
        const val TAG_PROFILE = "profile"
    }
}
