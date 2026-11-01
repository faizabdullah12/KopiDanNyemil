package com.kopidannyemil.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kopidannyemil.app.data.AppState
import com.kopidannyemil.app.data.MenuItem
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.databinding.ItemMenuBinding

class MenuAdapter(
    private var items: List<MenuItem>,
    private val onCartChanged: () -> Unit
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    fun submit(newItems: List<MenuItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val b = holder.binding
        val qty = AppState.cart[item.id] ?: 0

        b.imgMenu.setImageResource(item.imageRes)
        b.tvName.text = item.name
        b.tvDesc.text = "• ${item.description}"
        b.tvPrice.text = item.price.toRupiah()
        b.tvQty.text = qty.toString()

        val qtyVisibility = if (qty > 0) View.VISIBLE else View.GONE
        b.btnMinus.visibility = qtyVisibility
        b.tvQty.visibility = qtyVisibility

        b.btnPlus.setOnClickListener {
            AppState.addToCart(item.id)
            refreshRow(holder)
            onCartChanged()
        }
        b.btnMinus.setOnClickListener {
            AppState.removeFromCart(item.id)
            refreshRow(holder)
            onCartChanged()
        }
    }

    private fun refreshRow(holder: ViewHolder) {
        val pos = holder.bindingAdapterPosition
        if (pos != RecyclerView.NO_POSITION) notifyItemChanged(pos)
    }
}
