package com.ariesvelasquez.selectionprototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class GenericRecyclerViewAdapter<T>(
    private var items: MutableList<T>?,
    private val layoutId: Int,
    private var recyclerViewListener: GenericRecyclerViewListener? = null,
    private var onItemClickListener: (View, Int, T) -> Unit
) : RecyclerView.Adapter<GenericRecyclerViewAdapter.BindingHolder>() {

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val item: T = items!![position]
        holder.bind(item!!)

        if (holder.itemView.isEnabled) {
            holder.itemView.setOnClickListener {
                onItemClickListener(
                    holder.itemView,
                    position,
                    item
                )
            }
        }

//        if (position == (itemCount - 1)) {
//            recyclerViewListener?.onScrolledToLastPage()
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val v = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(v, layoutId, parent, false)
        return BindingHolder(binding)
    }

    class BindingHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Any) {
            binding.setVariable(BR.model, model)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int = items!!.size

    private fun applyAndAnimateRemovals(newModels: List<T>) {
        for (i in items!!.indices.reversed()) {
            val model = items!![i]
            if (!newModels.contains(model)) {
                removeItem(i)
            }
        }
    }

    private fun applyAndAnimateAdditions(newModels: List<T>) {
        var i = 0
        val count = newModels.size
        while (i < count) {
            val model = newModels[i]
            if (!items!!.contains(model)) {
                addItem(i, model)
            }
            i++
        }
    }

    private fun applyAndAnimateMovedItems(newModels: List<T>) {
        for (toPosition in newModels.indices.reversed()) {
            val model = newModels[toPosition]
            val fromPosition = items!!.indexOf(model)
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition)
            }
        }
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val model = items!!.removeAt(fromPosition)
        items!!.add(toPosition, model)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun removeItem(position: Int): T {
        val model = items!!.removeAt(position)
        notifyItemRemoved(position)
        return model
    }

    fun addItem(position: Int, model: T) {
        items!!.add(position, model)
        notifyItemInserted(position)
    }

    fun addItem(item: T) {
        items!!.add(item)
        notifyDataSetChanged()
    }

    fun addItems(items: MutableList<T>) {
        items.addAll(items)
        notifyDataSetChanged()
    }

    fun setItems(items: MutableList<T>) {
        applyAndAnimateRemovals(items)
        applyAndAnimateAdditions(items)
        applyAndAnimateMovedItems(items)
    }

    fun clear() {
        this.items!!.clear()
        notifyDataSetChanged()
    }

    interface GenericRecyclerViewListener {
        fun onScrolledToLastPage()
    }
}