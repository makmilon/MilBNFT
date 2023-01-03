package com.mobiria.bnft.ui.comman

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingRecyclerViewHolder<T : ViewDataBinding?>(val binding: T) : RecyclerView.ViewHolder(
    binding!!.root
)