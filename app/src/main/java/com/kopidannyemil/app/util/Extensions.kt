package com.kopidannyemil.app.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.kopidannyemil.app.MainActivity
import com.kopidannyemil.app.R
import com.kopidannyemil.app.WelcomeActivity
import com.kopidannyemil.app.data.OrderLine
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.databinding.ItemOrderLineBinding

fun Context.dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()

/** Buka MainActivity dan hapus semua activity sebelumnya. */
fun Activity.openMainAndClear() {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
}

/** Buka WelcomeActivity (Sign up) dan hapus semua activity sebelumnya. */
fun Activity.openWelcomeAndClear() {
    val intent = Intent(this, WelcomeActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
}

/** Isi LinearLayout dengan baris pesanan + baris total. */
fun LinearLayout.bindOrderLines(lines: List<OrderLine>, total: Int) {
    removeAllViews()
    val inflater = LayoutInflater.from(context)

    lines.forEach { line ->
        val row = ItemOrderLineBinding.inflate(inflater, this, false)
        row.tvLineName.text = "${line.qty}x ${line.item.name}"
        row.tvLineAmount.text = line.subtotal.toRupiah()
        addView(row.root)
    }

    if (lines.isNotEmpty()) {
        val divider = View(context)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            context.dpToPx(1)
        )
        params.topMargin = context.dpToPx(8)
        params.bottomMargin = context.dpToPx(8)
        divider.layoutParams = params
        divider.setBackgroundColor(ContextCompat.getColor(context, R.color.peach))
        addView(divider)

        val totalRow = ItemOrderLineBinding.inflate(inflater, this, false)
        totalRow.tvLineName.text = "Total"
        totalRow.tvLineName.setTypeface(null, Typeface.BOLD)
        totalRow.tvLineAmount.text = total.toRupiah()
        totalRow.tvLineAmount.setTypeface(null, Typeface.BOLD)
        totalRow.tvLineAmount.setTextColor(ContextCompat.getColor(context, R.color.caramel_dark))
        addView(totalRow.root)
    }
}
