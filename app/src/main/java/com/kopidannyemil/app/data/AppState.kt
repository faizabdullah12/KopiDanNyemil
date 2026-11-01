package com.kopidannyemil.app.data

/**
 * State aplikasi (akun, keranjang, pesanan terakhir).
 * Semua disimpan di memori, tanpa database.
 */
object AppState {

    private val accounts = mutableListOf(DummyData.defaultAccount)

    var currentUser: Account? = null
        private set

    /** id menu -> jumlah */
    val cart = mutableMapOf<Int, Int>()

    var lastOrder: OrderResult? = null
        private set

    /** Dipakai supaya MainActivity pindah ke tab Home setelah halaman konfirmasi ditutup. */
    var pendingHome: Boolean = false

    val displayName: String
        get() = currentUser?.fullName ?: "Nama"

    val cartCount: Int
        get() = cart.values.sum()

    val cartLines: List<OrderLine>
        get() = DummyData.menu.mapNotNull { m -> cart[m.id]?.let { OrderLine(m, it) } }

    val cartTotal: Int
        get() = cartLines.sumOf { it.subtotal }

    // ---------- Auth ----------

    /** Mengembalikan pesan error, atau null kalau berhasil. */
    fun login(username: String, password: String): String? {
        if (username.isBlank() || password.isBlank()) return "Username dan password wajib diisi"
        val account = accounts.find {
            it.username.equals(username.trim(), ignoreCase = true) && it.password == password
        } ?: return "Username atau password salah"
        currentUser = account
        return null
    }

    /** Mengembalikan pesan error, atau null kalau berhasil (langsung login). */
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
        pendingHome = false
    }

    // ---------- Keranjang & order ----------

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
