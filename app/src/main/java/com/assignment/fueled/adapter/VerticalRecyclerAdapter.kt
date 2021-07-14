package com.assignment.fueled.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.fueled.model.UserBase
import com.assignment.fueled.view.CellView
import kotlin.collections.ArrayList

class VerticalRecyclerAdapter(private var dataSet: ArrayList<UserBase>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var content: CellView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        content = CellView(parent.context)
        return content.viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        content.viewHolder = holder as CellView.ViewHolder
        if(dataSet?.isNotEmpty()!!){
            content.setData(dataSet?.get(position))
        }

    }

    override fun getItemCount(): Int {
        return 3
    }
}