package com.kopidannyemil.app.data

import androidx.annotation.DrawableRes
import com.kopidannyemil.app.R
import java.text.NumberFormat
import java.util.Locale

enum class Category(val label: String) {
    MINUMAN("Minuman"),
    MAKANAN("Makanan")
}

data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val category: Category,
    @DrawableRes val imageRes: Int
)

data class Account(
    val fullName: String,
    val username: String,
    val password: String
)

data class OrderLine(
    val item: MenuItem,
    val qty: Int
) {
    val subtotal: Int get() = item.price * qty
}

data class OrderResult(
    val receiverName: String,
    val address: String,
    val detail: String,
    val lines: List<OrderLine>,
    val total: Int
)

fun Int.toRupiah(): String =
    "Rp " + NumberFormat.getInstance(Locale.forLanguageTag("id-ID")).format(this)

object DummyData {
    val defaultAccount = Account(
        fullName = "Faiz",
        username = "faizabdfwz12",
        password = "123456"
    )

    val menu: List<MenuItem> = listOf(
        MenuItem(1, "Es Kopi Susu", "Kopi, susu dan gula aren", 18000, Category.MINUMAN, R.drawable.es_kopi_susu),
        MenuItem(2, "Roti Bakar Cokelat", "Roti bakar dengan meses cokelat", 12000, Category.MAKANAN, R.drawable.roti_bakar_cokelat),
        MenuItem(3, "Kentang Goreng", "Kentang goreng dengan saus sambal", 15000, Category.MAKANAN, R.drawable.kentang_goreng),
        MenuItem(4, "Kopi Hitam", "Kopi hitam dengan rasa khas", 12000, Category.MINUMAN, R.drawable.kopi_hitam),
        MenuItem(5, "Es Teh Manis", "Teh manis dingin dan segar", 8000, Category.MINUMAN, R.drawable.es_teh_manis),
        MenuItem(6, "Roti Bakar Keju", "Roti bakar isi keju dan susu", 14000, Category.MAKANAN, R.drawable.roti_bakar_keju),
        MenuItem(7, "Pisang Cokelat", "Pisang goreng dengan topping cokelat", 12000, Category.MAKANAN, R.drawable.pisang_coklat)
    )
}
