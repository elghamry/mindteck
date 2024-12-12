package com.test.mindteck.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.test.mindteck.databinding.ItemRecyclerBinding
import com.test.mindteck.model.ItemModel

class ItemAdapter(private var items: List<ItemModel>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemRecyclerBinding =
            ItemRecyclerBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = items[position]
        holder.binding.imgItem.setImageResource(items[position].image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newItems: List<ItemModel>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ItemRecyclerBinding

        init {
            this.binding = binding as ItemRecyclerBinding
        }

    }
}