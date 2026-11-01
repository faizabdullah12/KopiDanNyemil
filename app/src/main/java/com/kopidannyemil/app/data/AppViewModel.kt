package com.kopidannyemil.app.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class AppViewModel : ViewModel() {

    private val accounts = mutableStateListOf(DummyData.defaultAccount)

    var currentUser by mutableStateOf<Account?>(null)
        private set

    val cart = mutableStateMapOf<Int, Int>()

    var lastOrder by mutableStateOf<OrderResult?>(null)
        private set

    val displayName: String
        get() = currentUser?.fullName ?: "Nama"

    val cartCount: Int
        get() = cart.values.sum()

    val cartLines: List<OrderLine>
        get() = DummyData.menu.mapNotNull { m -> cart[m.id]?.let { OrderLine(m, it) } }

    val cartTotal: Int
        get() = cartLines.sumOf { it.subtotal }

    fun login(username: String, password: String): String? {
        if (username.isBlank() || password.isBlank()) return "Username dan password wajib diisi"
        val account = accounts.find {
            it.username.equals(username.trim(), ignoreCase = true) && it.password == password
        } ?: return "Username atau password salah"
        currentUser = account
        return null
    }

    fun register(fullName: String, username: String, password: String): String? {
        if (fullName.isBlank() || username.isBlank() || password.isBlank()) {
            return "Semua kolom wajib diisi"
        }
        if (username.trim().length < 3) return "Username minimal 3 karakter"
        if (password.length < 6) return "Password minimal 6 karakter"
        if (accounts.any { it.username.equals(username.trim(), ignoreCase = true) }) {
            return "Username sudah dipakai"
        }
        val account = Account(fullName.trim(), username.trim(), password)
        accounts.add(account)
        currentUser = account
        return null
    }

    fun updateName(newName: String): Boolean {
        val user = currentUser ?: return false
        if (newName.isBlank()) return false
        val updated = user.copy(fullName = newName.trim())
        val index = accounts.indexOfFirst { it.username == user.username }
        if (index >= 0) accounts[index] = updated
        currentUser = updated
        return true
    }

    fun logout() {
        currentUser = null
        cart.clear()
        lastOrder = null
    }

    fun addToCart(id: Int) {
        cart[id] = (cart[id] ?: 0) + 1
    }

    fun removeFromCart(id: Int) {
        val qty = cart[id] ?: return
        if (qty <= 1) cart.remove(id) else cart[id] = qty - 1
    }

    fun placeOrder(receiverName: String, address: String, detail: String) {
        val lines = cartLines
        if (lines.isEmpty()) return
        lastOrder = OrderResult(receiverName, address, detail, lines, cartTotal)
        cart.clear()
    }
}
